package open.api.coc.clans.clean.domain.player.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.exception.PlayerAlreadyExistsException;
import open.api.coc.clans.clean.domain.player.exception.PlayerNotFoundException;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerSearchQuery;
import open.api.coc.clans.clean.domain.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<Player> findAllPlayers(String accountType, String name) {
        PlayerSearchQuery query = PlayerSearchQuery.create(accountType, name);
        return playerRepository.findAll(query);
    }

    @Transactional(readOnly = true)
    public List<Player> findSummarizedPlayers(String accountType, String name) {
        PlayerSearchQuery query = PlayerSearchQuery.create(accountType, name);
        return playerRepository.findAllSummarized(query);
    }

    @Transactional(readOnly = true)
    public Player findById(String playerTag) {
        if (playerTag == null || playerTag.isEmpty()) {
            throw new IllegalArgumentException("playerTag can not be null or empty");
        }

        return playerRepository.findById(playerTag)
                               .orElseThrow(() -> new PlayerNotFoundException(playerTag));
    }

    @Transactional(readOnly = true)
    public void ensurePlayerDoesNotExist(String playerTag) {
        if (playerRepository.exists(playerTag)) {
            throw new PlayerAlreadyExistsException(playerTag);
        }
    }

    @Transactional
    public Player create(Player newPlayer) {
        newPlayer.changeNormalPlayer();
        return save(newPlayer);
    }

    @Transactional
    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public List<String> findAllPlayerTags() {
        return playerRepository.findAllPlayerTags();
    }

    @Transactional
    public void delete(String playerTag) {
        findById(playerTag);

        playerRepository.deleteById(playerTag);
    }

    @Transactional(readOnly = true)
    public List<Player> findTrophiesRanking(Integer pageSize) {
        return playerRepository.findTrophiesRanking(pageSize);
    }

    @Transactional(readOnly = true)
    public List<Player> findAttackWinsRanking(Integer pageSize) {
        return playerRepository.findAttackWinsRanking(pageSize);
    }
}
