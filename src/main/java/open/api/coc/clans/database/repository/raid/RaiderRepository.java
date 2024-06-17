package open.api.coc.clans.database.repository.raid;

import java.time.LocalDate;
import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RaiderRepository extends JpaRepository<RaiderEntity, Long> {

    @Query("select raider from RaiderEntity raider where raider.tag = :playerTag")
    List<RaiderEntity> findByTag(String playerTag);

    @Query("select raider from RaiderEntity raider where raider.name like CONCAT(:playerName, '%')")
    List<RaiderEntity> findByName(String playerName);

    @Query("select raider "
        + " from RaiderEntity raider "
        + " join fetch RaidEntity raid on raid.startDate = :startDate and raid.id = raider.raid.id"
        + " order by raider.resourceLooted desc")
    List<RaiderEntity> getRankingByStartDateAndLimit(LocalDate startDate, Pageable pageable);

}
