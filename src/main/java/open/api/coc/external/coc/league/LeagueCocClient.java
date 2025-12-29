package open.api.coc.external.coc.league;

import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LeagueCocClient implements LeagueApi {

    private final ClashOfClanConfig clashOfClanConfig;
    private final WebClient webClient;

    @Override
    public Optional<LabelList> findLeagues() {
        try {
            return webClient.get()
                            .uri(clashOfClanConfig.getLeaguesUri())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(clashOfClanConfig.getLeaguesUri(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(LabelList.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", clashOfClanConfig.getLeaguesUri(), e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<LabelList> findLeagueTiers() {
        try {
            return webClient.get()
                            .uri(clashOfClanConfig.getLeagueTiersUri())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(clashOfClanConfig.getLeagueTiersUri(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(LabelList.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", clashOfClanConfig.getLeagueTiersUri(), e);
            return Optional.empty();
        }
    }

    private void writeLog(String uri, ClientResponse response, String body) {
        log.warn("{} Request Failed. status={}, body={}", uri, response.statusCode(), body);
    }

}
