package open.api.coc.clans.database.repository.player;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerItemInfoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerHeroRepository extends JpaRepository<PlayerHeroEntity, PlayerItemInfoPK> {
}
