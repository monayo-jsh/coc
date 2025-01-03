package open.api.coc.clans.clean.domain.player.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.exception.PlayerAlreadyExistsException;
import open.api.coc.clans.clean.domain.player.exception.PlayerNotFoundException;
import open.api.coc.clans.clean.domain.player.external.client.PlayerClient;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerSearchQuery;
import open.api.coc.clans.clean.domain.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerClient playerClient;
    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<String> findAllTag() {
        PlayerSearchQuery query = PlayerSearchQuery.empty();
        return playerRepository.findAllTag(query);
    }

    @Transactional(readOnly = true)
    public List<String> findAllTagNotExists(List<String> playerTags) {
        PlayerSearchQuery query = PlayerSearchQuery.createWithTags(playerTags);
        List<String> existsPlayerTags = playerRepository.findAllTag(query);

        return playerTags.stream()
                         .filter(playerTag -> !existsPlayerTags.contains(playerTag))
                         .collect(Collectors.toList());
    }

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
    public List<Player> findTrophiesRanking(Integer pageSize) {
        return playerRepository.findTrophiesRanking(pageSize);
    }

    @Transactional(readOnly = true)
    public List<Player> findAttackWinsRanking(Integer pageSize) {
        return playerRepository.findAttackWinsRanking(pageSize);
    }

    @Transactional(readOnly = true)
    public Optional<Player> findById(String playerTag) {
        if (playerTag == null || playerTag.isEmpty()) {
            throw new IllegalArgumentException("playerTag can not be null or empty");
        }

        return playerRepository.findById(playerTag);
    }

    @Transactional(readOnly = true)
    public Player findByIdOrThrow(String playerTag) {
        return findById(playerTag).orElseThrow(() -> new PlayerNotFoundException(playerTag));
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

    @Transactional
    public void delete(String playerTag) {
        findByIdOrThrow(playerTag);

        playerRepository.deleteById(playerTag);
    }

    @Transactional
    public Player findOrCreate(String playerTag) {
        // 사용자를 서버에서 조회
        return findById(playerTag).orElseGet(() -> fetchAndCreate(playerTag));
    }

    @Transactional
    public Player fetchAndCreate(String playerTag) {
        Player latestPlayer = playerClient.findByTag(playerTag);
        return create(latestPlayer);
    }
}
