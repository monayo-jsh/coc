package open.api.coc.clans.database.repository.player;

import open.api.coc.clans.database.entity.player.PlayerHeroEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerHeroRepository extends JpaRepository<PlayerHeroEntity, PlayerItemPKEntity> {
}
