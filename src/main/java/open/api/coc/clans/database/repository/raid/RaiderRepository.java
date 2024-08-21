package open.api.coc.clans.database.repository.raid;

import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RaiderRepository extends JpaRepository<RaiderEntity, Long> {

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

}