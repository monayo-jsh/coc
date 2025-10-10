package open.api.coc.external.coc.league;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueCocClient implements LeagueApi {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;

    @Override
    public Optional<LabelList> findLeagues() {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getLeaguesUri())
                                             .retrieve()
                                             .body(LabelList.class));
    }

    @Override
    public Optional<LabelList> findLeagueTiers() {
        return Optional.ofNullable(restClient.get()
                                             .uri(clashOfClanConfig.getLeagueTiersUri())
                                             .retrieve()
                                             .body(LabelList.class));
    }

}
