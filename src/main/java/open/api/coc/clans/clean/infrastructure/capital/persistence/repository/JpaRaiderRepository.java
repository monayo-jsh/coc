package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaRaiderRepository extends JpaRepository<RaiderEntity, Long> {

    List<RaiderEntity> findAllByRaidId(Long raidId);

    @Query(
        nativeQuery = true,
        value = " select raider.* "
              + " from ( "
              + " select raider_seq, tag, name, attacks, resource_looted, raid_id, "
              + " rank() over(partition by tag order by raider_seq desc) as rank "
              + " from tb_raider "
              + " where tag = :playerTag "
              + " ) as raider "
              + " where raider.rank <= :limit "
              + " order by raider.tag "
    )
    List<RaiderEntity> findAllByPlayerTag(String playerTag, Integer limit);

    @Query(
        nativeQuery = true,
        value = " select raider.* "
              + " from ( "
              + " select raider_seq, tag, name, attacks, resource_looted, raid_id, "
              + " rank() over(partition by tag order by raider_seq desc) as rank "
              + " from tb_raider "
              + " where name like CONCAT(:playerName, '%') "
              + " ) as raider "
              + " where raider.rank <= :limit "
              + " order by raider.tag "
    )
    List<RaiderEntity> findAllByPlayerName(String playerName, Integer limit);

}
