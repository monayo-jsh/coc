package open.api.coc.clans.clean.domain.season.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SeasonRepository {

    Optional<LocalDate> findSeasonEndDateByBaseDate(LocalDate baseDate);

    List<LocalDate> findLatestSeasonEndDate(int limit);

    void save(LocalDate endDate);

    void remove(LocalDate localDate);

}
