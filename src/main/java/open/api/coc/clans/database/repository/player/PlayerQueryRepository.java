package open.api.coc.clans.database.repository.player;

import java.util.List;
import open.api.coc.clans.database.entity.player.PlayerEntity;

public interface PlayerQueryRepository {

    List<PlayerEntity> findAll();

    List<PlayerEntity> findAllByName(String name);
}
