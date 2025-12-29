package open.api.coc.clans.clean.infrastructure.capital.external.client;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.domain.capital.external.client.ClanCapitalClient;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.infrastructure.capital.external.dto.ClanCapitalRaidSeasonResponse;
import open.api.coc.clans.clean.infrastructure.capital.external.dto.ClanCapitalRaidSeasonsResponse;
import open.api.coc.clans.clean.infrastructure.capital.external.exception.ClanCapitalRaidSeasonException;
import open.api.coc.clans.clean.infrastructure.capital.external.mapper.ClanCapitalRaidSeasonMapper;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClanCapitalClientImpl implements ClanCapitalClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final WebClient webClient;

    private final ClanCapitalRaidSeasonMapper clanCapitalRaidSeasonMapper;

    @Override
    public ClanCapitalRaidSeason findCurrentSeasonByClanTag(String clanTag) {
        final int SEARCH_LIMIT = 1;

        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClansClanTagCapitalRaidSeasonsUri()).build(clanTag);

        try {
            Optional<ClanCapitalRaidSeasonsResponse> result = webClient.get()
                                                                      .uri(uriBuilder -> uriBuilder.path(uri.getPath())
                                                                                                   .queryParam("limit", SEARCH_LIMIT)
                                                                                                   .build())
                                                                      .retrieve()
                                                                      .onStatus(HttpStatusCode::isError,
                                                                                response ->
                                                                                    response.bodyToMono(String.class)
                                                                                            .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                                                            .then(Mono.empty()))
                                                                      .bodyToMono(ClanCapitalRaidSeasonsResponse.class)
                                                                      .map(Optional::of)
                                                                      .defaultIfEmpty(Optional.empty())
                                                                      .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));

            if (result.isEmpty()) {
                throw new ClanCapitalRaidSeasonException(clanTag);
            }

            ClanCapitalRaidSeasonsResponse clanCapitalRaidSeasonsResponse = result.get();

            if (clanCapitalRaidSeasonsResponse.getFirstItem() == null) {
                throw new ClanCapitalRaidSeasonException(clanTag);
            }

            return clanCapitalRaidSeasonMapper.toDomain(clanCapitalRaidSeasonsResponse.getFirstItem());
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw new ClanCapitalRaidSeasonException(clanTag);
        }
    }

    private void writeLog(String uri, ClientResponse response, String body) {
        log.warn("{} Request Failed. status={}, body={}", uri, response.statusCode(), body);
    }
}
