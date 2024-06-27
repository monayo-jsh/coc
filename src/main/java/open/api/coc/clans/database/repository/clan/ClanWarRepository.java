package open.api.coc.clans.database.repository.clan;

import java.time.LocalDateTime;
import java.util.Optional;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanWarRepository extends JpaRepository<ClanWarEntity, Long> {

    @Query("select clanWar from ClanWarEntity clanWar where clanWar.clanTag = :clanTag and clanWar.startTime = :startTime")
    Optional<ClanWarEntity> findByClanTagAndStartTime(String clanTag, LocalDateTime startTime);

}
