package open.api.coc.clans.database.repository.raid;

import java.time.LocalDate;
import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
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

    @Query(nativeQuery = true,
        value =
            " select raider.* "
          + " from ("
          + " select raider.*, "
          + " rank() over(partition by raider.tag order by raid.start_date desc) as rank "
          + " from tb_raider raider "
          + " join tb_raid raid on raid.raid_id = raider.raid_id "
          + " where raider.tag = :playerTag "
          + " ) as raider "
          + " where raider.rank <= :Limit "
          + " order by raider.tag "
    )
    List<RaiderEntity> findByTag(String playerTag, Integer Limit);

    @Query(nativeQuery = true,
          value =
              " select raider.* "
            + " from ("
            + " select raider.*, "
            + " rank() over(partition by raider.tag order by raid.start_date desc) as rank "
            + " from tb_raider raider "
            + " join tb_raid raid on raid.raid_id = raider.raid_id "
            + " where raider.name like CONCAT(:playerName, '%') "
            + " ) as raider "
            + " where raider.rank <= :Limit "
            + " order by raider.tag "
    )
    List<RaiderEntity> findByName(String playerName, Integer Limit);

    @Query("select raider"
        + " from RaiderEntity raider "
        + " join RaidEntity raid on raid.id = raider.raid.id "
        + " where raid.startDate = :startDate ")
    List<RaiderEntity> getMissingAttacks(LocalDate startDate);

    @Query("select raider.tag as tag, raider.name as name, raider.resourceLooted as score "
        + " from RaiderEntity raider "
        + " join fetch RaidEntity raid on raid.startDate in :startDate and raid.id = raider.raid.id"
        + " order by raider.resourceLooted desc")
    List<RankingHallOfFame> getRankingByStartDateAndLimit(LocalDate startDate, Pageable pageable);

    @Query("select raider.tag as tag, max(raider.name) as name, avg(raider.resourceLooted) as score "
        + " from RaiderEntity raider "
        + " join fetch RaidEntity raid on raid.startDate in (:startDates) and raid.id = raider.raid.id"
        + " group by raider.tag"
        + " having count(raider.tag) = :average"
        + " order by score desc")
    List<RankingHallOfFame> getRankingByStartDatesAndLimit(List<LocalDate> startDates, Integer average, Pageable pageable);
}
