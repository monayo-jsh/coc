package open.api.coc.clans.clean.infrastructure.clan.external.client;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.domain.clan.external.client.ClanClient;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanMember;
import open.api.coc.clans.clean.infrastructure.clan.external.dto.ClanMemberListResponse;
import open.api.coc.clans.clean.infrastructure.clan.external.dto.ClanResponse;
import open.api.coc.clans.clean.infrastructure.clan.external.exception.ClanClientException;
import open.api.coc.clans.clean.infrastructure.clan.external.mapper.ClanClientResponseMapper;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClanClientImpl implements ClanClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final WebClient webClient;

    private final ClanClientResponseMapper clanResponseMapper;

    public String makeRequestClanTag(String clanTag) {
        if (clanTag.startsWith("#")) {
            return clanTag;
        }

        return "#" + clanTag;
    }

    @Override
    public Clan findByTag(String clanTag) {
        String requestClanTag = makeRequestClanTag(clanTag);

        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClansClanTagUri()).build(requestClanTag);

        try {
            Optional<ClanResponse> result = webClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                                                     .retrieve()
                                                     .onStatus(HttpStatusCode::isError,
                                                               response ->
                                                                   response.bodyToMono(String.class)
                                                                           .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                                           .then(Mono.empty()))
                                                     .bodyToMono(ClanResponse.class)
                                                     .map(Optional::of)
                                                     .defaultIfEmpty(Optional.empty())
                                                     .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));

            if (result.isEmpty()) {
                throw ClanClientException.ofClan(requestClanTag);
            }

            return clanResponseMapper.toClan(result.get());
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw ClanClientException.ofClan(requestClanTag);
        }
    }

    @Override
    public List<ClanMember> findMembersByTag(String clanTag) {
        String requestClanTag = makeRequestClanTag(clanTag);

        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getClansClanTagUri()).build(requestClanTag);

        try {
            Optional<ClanMemberListResponse> result = webClient.get()
                                                     .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                                                     .retrieve()
                                                     .onStatus(HttpStatusCode::isError,
                                                               response ->
                                                                   response.bodyToMono(String.class)
                                                                           .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                                           .then(Mono.empty()))
                                                     .bodyToMono(ClanMemberListResponse.class)
                                                     .map(Optional::of)
                                                     .defaultIfEmpty(Optional.empty())
                                                     .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));

            if (result.isEmpty()) {
                throw ClanClientException.ofClan(requestClanTag);
            }

            return result.get()
                         .getItems()
                         .stream()
                         .map(clanResponseMapper::toClanMember)
                         .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            throw ClanClientException.ofClan(requestClanTag);
        }
    }

    private void writeLog(String uri, ClientResponse response, String body) {
        log.warn("{} Request Failed. status={}, body={}", uri, response.statusCode(), body);
    }
}
