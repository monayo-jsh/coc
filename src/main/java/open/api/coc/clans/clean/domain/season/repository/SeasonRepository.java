package open.api.coc.clans.clean.domain.season.repository;

import java.time.LocalDate;
import java.util.Optional;

public interface SeasonRepository {

    Optional<LocalDate> findSeasonEndDateByBaseDate(LocalDate baseDate);

}
