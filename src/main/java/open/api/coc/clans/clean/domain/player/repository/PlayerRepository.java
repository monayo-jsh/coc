package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerSearchQuery;

public interface PlayerRepository {

    List<Player> findAll(PlayerSearchQuery query);

    List<Player> findAllSummarized(PlayerSearchQuery query);

    List<String> findAllTag(PlayerSearchQuery query);

    Optional<Player> findById(String playerTag);

    boolean exists(String playerTag);

    Player save(Player newPlayer);
    void deleteById(String playerTag);

    List<Player> findTrophiesRanking(Integer pageSize);

    List<Player> findAttackWinsRanking(Integer pageSize);

}
