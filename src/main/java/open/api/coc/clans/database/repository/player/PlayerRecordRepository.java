package open.api.coc.clans.database.repository.player;

import open.api.coc.clans.database.entity.player.PlayerRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRecordRepository extends JpaRepository<PlayerRecordEntity, String> {
}
