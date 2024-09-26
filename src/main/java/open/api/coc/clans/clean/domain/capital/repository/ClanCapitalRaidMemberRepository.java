package open.api.coc.clans.clean.domain.capital.repository;

import java.time.LocalDate;
import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.domain.Pageable;

public interface ClanCapitalRaidMemberRepository {

    List<RaiderEntity> findAllByRaidId(Long raidId);

    List<RaiderEntity> findAllResourceLootedRankingByStartDateAndPage(LocalDate startDate, Pageable pageable);

}
