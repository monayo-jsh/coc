package open.api.coc.external.coc.clan;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.ClanMemberList;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ClanApiServiceImpl implements open.api.coc.external.coc.clan.ClanApiService {

    private final ClashOfClanConfig clashOfClanConfig;

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> findClanByClanTag(String clanTag) {
        return RestClient.create()
                         .get()
                         .uri(clashOfClanConfig.getClansClanTagUri(), clanTag)
                         .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                         .retrieve()
                         .body(Map.class);
    }

    @Override
    public Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit) {
        String uri = clashOfClanConfig.getClansClanTagCapitalRaidSeasons() + "?limit=" + limit;
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(uri, clanTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(ClanCapitalRaidSeasons.class));
    }

    @Override
    public Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag) {
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(clashOfClanConfig.getClansClanTagCurrentWar(), clanTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(ClanWar.class));
    }

    @Override
    public Optional<ClanMemberList> findClanMembersByClanTag(String clanTag) {
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(clashOfClanConfig.getClansClanMembers(), clanTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(ClanMemberList.class));
    }
}
