package open.api.coc.clans.clean.infrastructure.player.external.service;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.decorators.Decorators;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.external.client.PlayerClient;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.infrastructure.player.external.exception.PlayerClientException;
import open.api.coc.clans.clean.infrastructure.player.external.mapper.PlayerClientMapper;
import open.api.coc.clans.clean.infrastructure.player.external.model.PlayerResponse;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PlayerClientService implements PlayerClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;
    private final CircuitBreaker circuitBreaker;

    private final PlayerClientMapper playerClientMapper;

    public String makeRequestPlayerTag(String playerTag) {
        if (playerTag.startsWith("#")) {
            return playerTag;
        }

        return "#" + playerTag;
    }
    
    @Override
    public Player findByTag(String playerTag) {
        String requestPlayerTag = makeRequestPlayerTag(playerTag);
        try {
            PlayerResponse playerResponse = Decorators.ofSupplier(() -> restClient.get()
                                                                                  .uri(clashOfClanConfig.getPlayerUri(), requestPlayerTag)
                                                                                  .retrieve()
                                                                                  .body(PlayerResponse.class))
                                                      .withCircuitBreaker(circuitBreaker)
                                                      .get();

            if (Objects.isNull(playerResponse)) {
                throw new PlayerClientException(requestPlayerTag);
            }

            return playerClientMapper.toPlayer(playerResponse);
        } catch (CallNotPermittedException ex) {
            PlayerClientException playerClientException = new PlayerClientException(requestPlayerTag);
            playerClientException.addExtraMessage(ex.getMessage());
            throw playerClientException;
        } catch (Exception e) {
            PlayerClientException playerClientException = new PlayerClientException(requestPlayerTag);
            playerClientException.addExtraMessage(e.getMessage());
            throw playerClientException;
        }
    }

}
