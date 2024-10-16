package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlayerRecordRepository extends JpaRepository<PlayerRecordEntity, String> {
}
