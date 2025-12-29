package open.api.coc.external.coc.clan;

import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.infrastructure.clan.external.exception.ClanClientException;
import open.api.coc.clans.clean.infrastructure.player.external.exception.PlayerClientException;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.player.Player;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanApiServiceImpl implements ClanApiService {

    private final ClashOfClanConfig clashOfClanConfig;
    private final WebClient webClient;

    @Override
    public Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag) {
        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClansClanTagCurrentWarUri()).build(clanTag);

        try {
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(ClanWar.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw ClanClientException.ofClan(clanTag);
        }
    }

    @Override
    public Optional<Player> findPlayerBy(String playTag) {
        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getPlayerUri()).build(playTag);

        try {
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(Player.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw new PlayerClientException(playTag);
        }
    }

    @Override
    public Optional<ClanWar> findLeagueWarByRoundTag(String roundTag) {
        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClanWarLeagueUri()).build(roundTag);

        try {
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(ClanWar.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw ClanClientException.ofClan(roundTag);
        }
    }

    @Override
    public Optional<ClanCurrentWarLeagueGroup> findClanCurrentWarLeagueGroupBy(String clanTag) {
        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClansClanTagCurrentLeagueGroupUri()).build(clanTag);

        try {
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(ClanCurrentWarLeagueGroup.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw ClanClientException.ofClan(clanTag);
        }
    }

    @Override
    public Optional<ClanWar> findWarLeagueByWarTag(String warTag) {
        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClanWarLeagueUri()).build(warTag);

        try {
            return webClient.get()
                            .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                            .retrieve()
                            .onStatus(HttpStatusCode::isError,
                                      response ->
                                          response.bodyToMono(String.class)
                                                  .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                  .then(Mono.empty()))
                            .bodyToMono(ClanWar.class)
                            .map(Optional::of)
                            .defaultIfEmpty(Optional.empty())
                            .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw ClanClientException.ofClan(warTag);
        }
    }

    private void writeLog(String uri, ClientResponse response, String body) {
        log.warn("{} Request Failed. status={}, body={}", uri, response.statusCode(), body);
    }
}
