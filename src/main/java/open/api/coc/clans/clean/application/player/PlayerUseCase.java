package open.api.coc.clans.clean.application.player;


import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.player.mapper.PlayerUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanAssignService;
import open.api.coc.clans.clean.domain.clan.service.ClanLeagueAssignService;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.league.service.LeagueService;
import open.api.coc.clans.clean.domain.player.external.client.PlayerClient;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.service.PlayerDonationService;
import open.api.coc.clans.clean.domain.player.service.PlayerRecordService;
import open.api.coc.clans.clean.domain.player.service.PlayerService;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerUseCase {

    private final PlayerClient playerClient;

    private final PlayerService playerService;
    private final PlayerRecordService playerRecordService;
    private final PlayerDonationService playerDonationService;

    private final ClanService clanService;
    private final ClanAssignService clanAssignService;
    private final ClanLeagueAssignService clanLeagueAssignService;


    private final LeagueService leagueService;

    private final PlayerUseCaseMapper playerUseCaseMapper;

    @Transactional(readOnly = true)
    public List<PlayerResponse> getAllPlayers(String viewMode) {
        // 플레이어 목록을 조회한다.
        List<Player> players;
        if ("summary".equals(viewMode)) { // 확장될 경우 Enum 등으로 객체 처리
            players = playerService.findSummarizedPlayers();
        } else {
            players = playerService.findAllPlayers();
        }

        return mapToPlayerRespons(players);
    }

    private List<PlayerResponse> mapToPlayerRespons(List<Player> players) {
        // 클랜 정보를 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(players.stream().map(Player::getClanTag).distinct().toList());

        // 리그 정보를 조회한다.
        Map<Integer, League> leagueMap = leagueService.findAllMapByIds(players.stream().map(Player::getLeagueId).distinct().toList());

        // 플레이어 목록을 반환한다.
        return players.stream()
                      .map(player -> mapToPlayerResponse(player, clanMap, leagueMap))
                      .toList();
    }


    private PlayerResponse mapToPlayerResponse(Player player, Map<String, Clan> clanMap, Map<Integer, League> leagueMap) {
        Clan clan = null;
        League league = null;
        if (player.isJoinedClan()) {
            clan = clanMap.get(player.getClanTag());
        }
        if (player.isInLeague()) {
            league = leagueMap.get(player.getLeagueId());
        }

        return playerUseCaseMapper.toPlayerResponse(player, clan, league);
    }

    @Transactional(readOnly = true)
    public List<String> getAllPlayerTags() {
        // 플레이어 태그 목록을 반환한다.
        return playerService.findAllPlayerTags();
    }

    @Transactional(readOnly = true)
    public PlayerResponse getPlayer(String playerTag) {
        // 플레이어를 조회한다.
        Player player = playerService.findById(playerTag);

        // 응답한다.
        return mapToPlayerResponse(player);
    }

    private PlayerResponse mapToPlayerResponse(Player player) {
        // 플레이어 클랜 정보를 조회한다.
        Clan clan = null;
        if (player.isJoinedClan()) {
            try {
                // 서버에서 등록된 클랜이 아닐 수 있음
                clan = clanService.findById(player.getClanTag());
            } catch (Exception ignore) {}
        }

        // 플레이어 리그 정보를 조회한다.
        League league = null;
        if (player.isInLeague()) {
            league = leagueService.findById(player.getLeagueId());
        }

        return playerUseCaseMapper.toPlayerResponse(player, clan, league);
    }

    @Transactional
    public PlayerResponse registerPlayer(String playerTag) {
        // 등록된 플레이어를 검증한다.
        playerService.ensurePlayerDoesNotExist(playerTag);

        // COC 플레이어 최신 정보를 조회한다.
        Player latestPlayer = playerClient.findByTag(playerTag);

        // 플레이어가 가입한 클랜 데이터 생성
        clanService.createIfNotExists(latestPlayer.getClanTag());

        // 플레리어를 저장한다.
        Player savePlayer = playerService.create(latestPlayer);

        // 응답한다.
        return mapToPlayerResponse(savePlayer);
    }

    @Transactional
    public void synchronizePlayer(String playerTag) {
        // 서버의 플레이어 정보를 조회한다.
        Player player = playerService.findById(playerTag);

        // COC 플레이어 최신 정보를 조회한다.
        Player latestPlayer = playerClient.findByTag(playerTag);

        // 플레이어가 가입한 클랜 데이터 생성
        clanService.createIfNotExists(latestPlayer.getClanTag());

        // 플레이어의 트로피, 공/방 변화를 기록한다.
        playerRecordService.createHistory(player, latestPlayer);

        // 플레이어의 지원 통계를 기록한다.
        playerDonationService.collect(player, latestPlayer);

        // 서버의 플레이어 정보를 현행화한다.
        player.changeInfo(latestPlayer);

        // 플레이어를 저장한다.
        playerService.save(player);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void synchronizePlayerFromSchedule(String taskName, String playerTag) {
        try {
            synchronizePlayer(playerTag);
        } catch (Exception e) {
            log.error("[%s] 플레이어 동기화 실패: %s".formatted(taskName, playerTag), e);
        }
    }

    @Transactional
    public void removePlayer(String playerTag) {
        // 최근 클랜 배정 삭제
        clanAssignService.cancelRecently(playerTag);

        // 최근 리그 배정 삭제
        clanLeagueAssignService.cancelRecently(playerTag);

        // 플레이어 삭제
        playerService.delete(playerTag);
    }
}
