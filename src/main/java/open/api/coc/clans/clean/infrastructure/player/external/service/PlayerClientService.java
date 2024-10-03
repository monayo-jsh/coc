package open.api.coc.clans.clean.infrastructure.player.external.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.external.client.PlayerClient;
import open.api.coc.clans.clean.domain.player.external.model.PlayerResponse;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.infrastructure.player.external.exception.PlayerClientException;
import open.api.coc.clans.clean.infrastructure.player.external.mapper.PlayerClientMapper;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class PlayerClientService implements PlayerClient {

    private final ClashOfClanConfig clashOfClanConfig;
    private final RestClient restClient;

    private final PlayerClientMapper playerClientMapper;

    @Override
    public Player findByTag(String playerTag) {
        PlayerResponse playerResponse = restClient.get()
                                                  .uri(clashOfClanConfig.getPlayerUri(), playerTag)
                                                  .retrieve()
                                                  .body(PlayerResponse.class);

        if (Objects.isNull(playerResponse)) {
            throw new PlayerClientException(playerTag);
        }

        return playerClientMapper.toPlayer(playerResponse);
    }

}
