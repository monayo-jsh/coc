package open.api.coc.external.coc.clan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import open.api.coc.external.coc.clan.domain.player.Player;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanApiServiceImpl implements ClanApiService {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;

    @Override
    public Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getClansClanTagCurrentWarUri(), clanTag)
                                             .retrieve()
                                             .body(ClanWar.class));
    }

    @Override
    public Optional<Player> findPlayerBy(String playTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getPlayerUri(), playTag)
                                             .retrieve()
                                             .body(Player.class));
    }

    @Override
    public Optional<ClanWar> findLeagueWarByRoundTag(String roundTag) {
        return Optional.ofNullable(restClient.get()
                .uri(clashOfClanConfig.getClanWarLeagueUri(), roundTag)
                .retrieve()
                .body(ClanWar.class));
    }

    @Override
    public Optional<ClanCurrentWarLeagueGroup> findClanCurrentWarLeagueGroupBy(String clanTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getClansClanTagCurrentLeagueGroupUri(), clanTag)
                                             .retrieve()
                                             .body(ClanCurrentWarLeagueGroup.class));
    }

    @Override
    public Optional<ClanWar> findWarLeagueByWarTag(String warTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getClanWarLeagueUri(), warTag)
                                             .retrieve()
                                             .body(ClanWar.class));
    }

}
