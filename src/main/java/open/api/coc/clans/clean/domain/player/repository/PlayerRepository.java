package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.player.model.Player;

public interface PlayerRepository {

    List<Player> findAll(String accountType);

    List<Player> findAllSummarized(String accountType);

    List<String> findAllPlayerTags();

    Optional<Player> findById(String playerTag);

    boolean exists(String playerTag);

    Player save(Player newPlayer);
    void deleteById(String playerTag);

    List<Player> findTrophiesRanking(Integer pageSize);

}
