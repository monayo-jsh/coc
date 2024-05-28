package open.api.coc.clans.database.repository.raid;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.database.entity.raid.RaidEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RaidRepository extends JpaRepository<RaidEntity, Long> {
    @Query("SELECT entity FROM RaidEntity entity JOIN FETCH entity.raiderEntityList WHERE entity.startDate =:startDate")
    List<RaidEntity> getLastWeekRaidStatistics(LocalDate startDate, Pageable pageable);

    @Query("select raidEntity from RaidEntity raidEntity join fetch raidEntity.raiderEntityList where raidEntity.clanTag = :clanTag and raidEntity.startDate = :startDate")
    Optional<RaidEntity> findByClanTagAndStartDate(String clanTag, LocalDate startDate);
}
