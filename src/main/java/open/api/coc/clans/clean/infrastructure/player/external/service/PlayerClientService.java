package open.api.coc.clans.clean.infrastructure.player.external.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.time.Duration;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.domain.player.external.client.PlayerClient;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.infrastructure.player.external.exception.PlayerClientException;
import open.api.coc.clans.clean.infrastructure.player.external.mapper.PlayerClientMapper;
import open.api.coc.clans.clean.infrastructure.player.external.model.PlayerResponse;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PlayerClientService implements PlayerClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final WebClient webClient;

    private final CircuitBreaker circuitBreaker;

    private final PlayerClientMapper playerClientMapper;

    public PlayerClientService(ClashOfClanConfig clashOfClanConfig, WebClient webClient,
                               CircuitBreakerRegistry circuitBreakerRegistry,
                               PlayerClientMapper playerClientMapper) {
        this.clashOfClanConfig = clashOfClanConfig;
        this.webClient = webClient;
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("clashOfClanApi");
        this.playerClientMapper = playerClientMapper;
    }

    public String makeRequestPlayerTag(String playerTag) {
        if (playerTag.startsWith("#")) {
            return playerTag;
        }

        return "#" + playerTag;
    }
    
    @Override
    public Player findByTag(String playerTag) {
        String requestPlayerTag = makeRequestPlayerTag(playerTag);

        URI uri = UriComponentsBuilder.fromPath(clashOfClanConfig.getPlayerUri()).build(requestPlayerTag);

        try {
            Optional<PlayerResponse> result = webClient.get()
                                                       .uri(uriBuilder -> uriBuilder.path(uri.getPath()).build())
                                                       .retrieve()
                                                       .onStatus(HttpStatusCode::isError,
                                                                 response ->
                                                                     response.bodyToMono(String.class)
                                                                             .doOnNext(body -> writeLog(uri.toString(), response, body))
                                                                             .flatMap(body -> Mono.error(new RuntimeException(body))))
                                                       .bodyToMono(PlayerResponse.class)
                                                       .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                                                       .map(Optional::of)
                                                       .defaultIfEmpty(Optional.empty())
                                                       .block(Duration.ofSeconds(clashOfClanConfig.getReadTimeout().getSeconds()));

            if (result.isEmpty()) {
                throw new PlayerClientException(requestPlayerTag);
            }

            return playerClientMapper.toPlayer(result.get());
        } catch (CallNotPermittedException e) {
            // Circuit OPEN
            PlayerClientException playerClientException = new PlayerClientException(requestPlayerTag);
            playerClientException.addExtraMessage("Circuit breaker OPEN");
            throw playerClientException;
        } catch (Exception e) {
            log.warn("{} Request Call Failed. ", uri, e);
            PlayerClientException playerClientException = new PlayerClientException(requestPlayerTag);
            playerClientException.addExtraMessage(e.getMessage());
            throw playerClientException;
        }
    }

    private void writeLog(String uri, ClientResponse response, String body) {
        log.warn("{} Request Failed. status={}, body={}", uri, response.statusCode(), body);
    }
}
