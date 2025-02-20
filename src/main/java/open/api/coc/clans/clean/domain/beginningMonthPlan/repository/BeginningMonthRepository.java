package open.api.coc.clans.clean.domain.beginningMonthPlan.repository;

import java.time.LocalDate;
import java.util.Optional;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;

public interface BeginningMonthRepository {

    Optional<BeginningMonth> findByMonth(LocalDate month);
    BeginningMonth save(BeginningMonth beginningMonth);

}
