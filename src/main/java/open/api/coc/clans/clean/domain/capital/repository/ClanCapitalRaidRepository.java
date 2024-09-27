package open.api.coc.clans.clean.domain.capital.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import org.springframework.data.domain.Pageable;

public interface ClanCapitalRaidRepository {

    ClanCapitalRaid save(ClanCapitalRaid clanCapitalRaid);


    Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDate startDate);

    List<ClanCapitalRaid> findAllByIds(List<Long> raidIds);

    List<ClanCapitalRaid> findAllWithRaiderByStartDate(LocalDate startDate);

    LocalDate findLatestStartDate();

    List<LocalDate> findLatestStartDates(Pageable pageable);

}
