package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.player.model.Player;

public interface PlayerRepository {

    List<Player> findAll();

}
