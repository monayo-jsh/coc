package open.api.coc.clans.clean.application.player;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.player.mapper.PlayerUseCaseMapper;
import open.api.coc.clans.clean.application.player.model.PlayerListSearchQuery;
import open.api.coc.clans.clean.application.player.model.PlayerSupportUpdateBulkCommand;
import open.api.coc.clans.clean.application.player.model.PlayerSupportUpdateCommand;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanAssignedPlayer;
import open.api.coc.clans.clean.domain.clan.service.ClanAssignService;
import open.api.coc.clans.clean.domain.clan.service.ClanGameService;
import open.api.coc.clans.clean.domain.clan.service.ClanLeagueAssignService;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.league.service.LeagueService;
import open.api.coc.clans.clean.domain.player.external.client.PlayerClient;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationDTO;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerDonationReceiveDTO;
import open.api.coc.clans.clean.domain.player.model.dto.RankingHeroEquipmentDTO;
import open.api.coc.clans.clean.domain.player.service.PlayerDonationService;
import open.api.coc.clans.clean.domain.player.service.PlayerLegendRecordService;
import open.api.coc.clans.clean.domain.player.service.PlayerRankingService;
import open.api.coc.clans.clean.domain.player.service.PlayerService;
import open.api.coc.clans.clean.domain.player.service.PlayerSupportService;
import open.api.coc.clans.clean.presentation.common.dto.RankingHallOfFameResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerLegendRecordResponse;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import open.api.coc.clans.clean.presentation.player.dto.RankingHallOfFameDonationResponse;
import open.api.coc.clans.clean.presentation.player.dto.RankingHeroEquipmentResponse;
import open.api.coc.clans.common.config.HallOfFameConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerUseCase {

    private final HallOfFameConfig hallOfFameConfig;
    private final PlayerClient playerClient;

    private final PlayerService playerService;
    private final PlayerSupportService supportService;

    private final PlayerRankingService rankingService;

    private final ClanGameService clanGameService;
    private final PlayerLegendRecordService legendRecordService;
    private final PlayerDonationService playerDonationService;

    private final ClanService clanService;
    private final ClanAssignService clanAssignService;
    private final ClanLeagueAssignService clanLeagueAssignService;

    private final LeagueService leagueService;

    private final PlayerUseCaseMapper playerUseCaseMapper;

    @Transactional(readOnly = true)
    public List<PlayerResponse> getAllPlayers(PlayerListSearchQuery query) {
        // 플레이어 목록을 조회한다.
        List<Player> players;
        if (query.isSummaryViewMode()) {
            players = playerService.findSummarizedPlayers(query.accountType(), query.name());
        } else {
            players = playerService.findAllPlayers(query.accountType(), query.name());
        }

        if (players.isEmpty()) return Collections.emptyList();

        return mapToPlayerResponse(players);
    }

    private List<PlayerResponse> mapToPlayerResponse(List<Player> players) {
        // 클랜 정보를 조회한다.
        Map<String, Clan> clanMap = clanService.findAllMapByIds(players.stream().filter(Player::isJoinedClan).map(Player::getClanTag).distinct().toList());

        // 리그 정보를 조회한다.
        Map<Integer, League> leagueMap = leagueService.findAllMapByIds(players.stream().filter(Player::isInLeague).map(Player::getLeagueId).distinct().toList());

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
        return playerService.findAllTag();
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
            // 서버에서 등록된 클랜이 아닐 수 있음
            try {
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
        legendRecordService.createHistory(player, latestPlayer);

        // 플레이어의 지원 통계를 기록한다.
        playerDonationService.collect(player, latestPlayer);

        // 클랜 게임 수집
        clanGameService.collect(latestPlayer);

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
        clanAssignService.excludeRecently(playerTag);

        // 최근 리그 배정 삭제
        clanLeagueAssignService.excludeRecently(playerTag);

        // 플레이어 삭제
        playerService.delete(playerTag);
    }

    @Transactional
    public void changePlayerSupportType(PlayerSupportUpdateCommand command) {
        // 플레이어를 조회한다.
        Player player = playerService.findById(command.playerTag());

        // 플레이어의 지원 계정 유형을 설정한다.
        player.changeSupportType(command.supportYn());

        // 플레이어 계정 유형을 적용한다.
        playerService.save(player);

        // 지원 계정 전환 요청 시 배정된 클랜을 제거한다.
        if (command.isSupportPlayer()) {
            clanAssignService.excludeRecently(player.getTag());
        }
    }

    @Transactional
    public void changePlayerSupportTypeBulk(PlayerSupportUpdateBulkCommand command) {
        // 기존에 설정된 지원계정을 초기화한다.
        supportService.resetAll();

        // 지원 계정 일괄 설정한다.
        long updatedSupportPlayerCount = supportService.updateBulk(command.playerTags());

        // 지원계정 전환 시 배정된 클랜 제외한다.
        clanAssignService.excludeRecently(command.playerTags());

        // 요청한 계정 모두 처리된 경우 프로세스 종료한다.
        if (command.isRequestCompleted(updatedSupportPlayerCount)) return;

        // 그렇지 않은 경우 미등록 계정 태그가 존재하여 신규 계정 등록 및 지원 계정 설정한다.
        List<String> tagsThatNeedToBeRegistered = playerService.findAllTagNotExists(command.playerTags());
        for(String playerTag : tagsThatNeedToBeRegistered) {
            try {
                // COC 플레이어 최신 정보를 조회한다.
                Player latestPlayer = playerClient.findByTag(playerTag);

                // 플레이어가 가입한 클랜 데이터 생성
                clanService.createIfNotExists(latestPlayer.getClanTag());

                // 지원계정 설정
                latestPlayer.changeSupportPlayer();

                // 플레리어를 저장한다.
                playerService.save(latestPlayer);
            } catch (Exception e) {
                log.error("[not registered player] tag: {}, reason: {}", playerTag, e.getMessage());
            }
        }
    }

    @Transactional(readOnly = true)
    public List<PlayerLegendRecordResponse> getLegendRecord(String playerTag) {
        List<PlayerRecordHistory> legendRecords = legendRecordService.findAllLatest(playerTag);
        return legendRecords.stream()
                            .map(playerUseCaseMapper::toPlayerLegendRecordResponse)
                            .toList();
    }


    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingTrophies() {
        List<Player> players = playerService.findTrophiesRanking(hallOfFameConfig.getRanking());

        return players.stream()
                      .map(playerUseCaseMapper::toRankingTrophiesResponse)
                      .toList();
    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingAttackWins() {
        List<Player> players = playerService.findAttackWinsRanking(hallOfFameConfig.getRanking());

        return players.stream()
                      .map(playerUseCaseMapper::toRankingAttackWinsResponse)
                      .toList();
    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameDonationResponse> getRankingDonations() {
        // 현재 시즌 종료일을 가져온다.
        LocalDate currentLeagueSeasonEndDate = playerDonationService.getLeagueSeasonEndDate();

        // 플레이어 지원 랭킹 목록을 가져온다.
        List<PlayerDonationDTO> donations = playerDonationService.findDonationRanking(currentLeagueSeasonEndDate, hallOfFameConfig.getRanking());

        return donations.stream()
                        .map(playerUseCaseMapper::toRankingDonationResponse)
                        .toList();
    }

    @Transactional(readOnly = true)
    public List<RankingHallOfFameResponse> getRankingDonationReceived() {
        // 현재 시즌 종료일을 가져온다.
        LocalDate currentLeagueSeasonEndDate = playerDonationService.getLeagueSeasonEndDate();

        // 플레이어 지원 랭킹 목록을 가져온다.
        List<PlayerDonationReceiveDTO> donations = playerDonationService.findDonationReceivedRanking(currentLeagueSeasonEndDate, hallOfFameConfig.getRanking());

        return donations.stream()
                        .map(playerUseCaseMapper::toRankingDonationReceiveResponse)
                        .toList();
    }

    public PlayerResponse getPlayerFromExternal(String playerTag) {
        // COC API 조회한다.
        Player latestPlayer = playerClient.findByTag(playerTag);

        // 플레이어를 응답한다.
        return mapToPlayerResponse(latestPlayer);
    }

    public List<RankingHeroEquipmentResponse> getRankingHeroEquipments(String clanTag) {
        // 최근 배정일을 가져온다.
        String latestAssignedDate = clanAssignService.findLatestAssignedDate();

        // 클랜에 최근 배정 목록을 가져온다.
        List<ClanAssignedPlayer> clanAssignedPlayers = clanAssignService.findAll(latestAssignedDate, clanTag);

        if (clanAssignedPlayers.isEmpty()) return Collections.emptyList();

        // 영웅 장비 랭킹을 구한다.
        List<String> targetPlayerTags = clanAssignedPlayers.stream()
                                                           .map(ClanAssignedPlayer::getPlayerTag)
                                                           .toList();
        List<RankingHeroEquipmentDTO> rankingHeroEquipmentDTOs = rankingService.findHeroEquipmentRanking(targetPlayerTags);

        // 응답한다.
        return rankingHeroEquipmentDTOs.stream()
                                       .map(playerUseCaseMapper::toRankingHeroEquipmentResponse)
                                       .toList();
    }

}
