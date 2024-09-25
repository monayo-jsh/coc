package open.api.coc.clans.clean.domain.capital.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;

public interface ClanCapitalRaidRepository {

    Optional<RaidEntity> findByClanTagAndStartDate(String clanTag, LocalDate startDate);

    RaidEntity save(RaidEntity entity);

    void update(RaidEntity raidEntity);

    LocalDate findLatestStartDate();

    List<RaidEntity> findAllWithRaiderByStartDate(LocalDate startDate);

}
