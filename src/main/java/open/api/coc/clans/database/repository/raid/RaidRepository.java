package open.api.coc.clans.database.repository.raid;

import open.api.coc.clans.database.entity.raid.RaidEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RaidRepository extends JpaRepository<RaidEntity, Long> {
    @Query("SELECT entity FROM RaidEntity entity JOIN FETCH entity.raiderEntityList ORDER BY entity.id DESC")
    List<RaidEntity> getLastWeekRaidStatistics(Pageable pageable);
}
