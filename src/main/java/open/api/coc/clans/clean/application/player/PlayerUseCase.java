package open.api.coc.clans.clean.application.player;


import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.player.mapper.PlayerUseCaseMapper;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.service.ClanService;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.league.service.LeagueService;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.service.PlayerService;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerUseCase {

    private final PlayerService playerService;

    private final ClanService clanService;
    private final LeagueService leagueService;

    private final PlayerUseCaseMapper playerUseCaseMapper;

    @Transactional(readOnly = true)
    public List<PlayerResponse> getAllPlayers() {
        // 플레이어 목록을 조회한다.
        List<Player> players = playerService.getAllPlayers();

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

}
