package open.api.coc.clans.clean.domain.capital.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import org.springframework.data.domain.Pageable;

public interface ClanCapitalRaidRepository {

    RaidEntity save(RaidEntity entity);

    void update(RaidEntity raidEntity);

    Optional<RaidEntity> findByClanTagAndStartDate(String clanTag, LocalDate startDate);

    List<RaidEntity> findAllByIds(List<Long> raidIds);

    List<RaidEntity> findAllWithRaiderByStartDate(LocalDate startDate);

    LocalDate findLatestStartDate();

    List<LocalDate> findLatestStartDates(Pageable pageable);

}
