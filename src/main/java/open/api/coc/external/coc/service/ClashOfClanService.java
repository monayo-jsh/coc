package open.api.coc.external.coc.service;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import open.api.coc.external.coc.ClashOfClansApi;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class ClashOfClanService implements ClashOfClansApi {

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
    @SuppressWarnings("unchecked")
    public Map<String, Object> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit) {
        String uri = clashOfClanConfig.getClansClanTagCapitalRaidSeasons() + "?limit=" + limit;
        return RestClient.create()
                         .get()
                         .uri(uri, clanTag)
                         .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                         .retrieve()
                         .body(Map.class);
    }

}
