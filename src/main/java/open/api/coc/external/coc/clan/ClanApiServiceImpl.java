package open.api.coc.external.coc.clan;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.Clan;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanMemberList;
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
    private final ObjectMapper objectMapper;

    @Override
    public Optional<Clan> findClanByClanTag(String clanTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getClansClanTagUri(), clanTag)
                                             .retrieve()
                                             .body(Clan.class));
    }

    @Override
    public Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit) {
        String uri = clashOfClanConfig.getClansClanTagCapitalRaidSeasonsUri() + "?limit=" + limit;
        return Optional.ofNullable(restClient.get()
                                             .uri(uri, clanTag)
                                             .retrieve()
                                             .body(ClanCapitalRaidSeasons.class));
    }

    @Override
    public Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getClansClanTagCurrentWarUri(), clanTag)
                                             .retrieve()
                                             .body(ClanWar.class));
    }

    @Override
    public ClanMemberList findClanMembersByClanTag(String clanTag) {
        return restClient.get()
                         .uri(clashOfClanConfig.getClansClanMembersUri(), clanTag)
                         .retrieve()
                         .body(ClanMemberList.class);
    }

    @Override
    public Optional<Player> findPlayerBy(String playTag) {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getPlayerUri(), playTag)
                                             .retrieve()
                                             .body(Player.class));
    }

    @Override
    public List<LinkedHashMap<String,List<String>>> findClanWarLeagueRoundTags(String clanTag) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(
                restClient.get().uri(clashOfClanConfig.getClansClanTagCurrentLeagueGroupUri(), clanTag)
                .retrieve().body(String.class));
        return objectMapper.convertValue(rootNode.get("rounds"), List.class);
    }

    @Override
    public Optional<ClanWar> findLeagueWarByRoundTag(String roundTag) {
        return Optional.ofNullable(restClient.get()
                .uri(clashOfClanConfig.getClanWarLeagueUri(), roundTag)
                .retrieve()
                .body(ClanWar.class));
    }

    @Override
    public Optional<LabelList> findLeagues() {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getLeaguesUri())
                                             .retrieve()
                                             .body(LabelList.class));
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
