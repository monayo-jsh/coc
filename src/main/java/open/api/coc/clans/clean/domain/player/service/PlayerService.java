package open.api.coc.clans.clean.domain.player.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.exception.PlayerNotFoundException;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Player findById(String playerTag) {
        if (playerTag == null || playerTag.isEmpty()) {
            throw new IllegalArgumentException("playerTag can not be null or empty");
        }

        return playerRepository.findById(playerTag)
                               .orElseThrow(() -> new PlayerNotFoundException(playerTag));
    }
}
