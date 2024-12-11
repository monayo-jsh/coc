package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPlayerRepository extends JpaRepository<PlayerEntity, String> {
}
