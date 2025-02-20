package open.api.coc.clans.clean.domain.beginningMonthPlan.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;

public interface BeginningMonthRepository {

    List<BeginningMonth> findAll(int limit);
    BeginningMonth findLatest();

    Optional<BeginningMonth> findByMonth(LocalDate month);
    Optional<BeginningMonth> findById(Long id);

    BeginningMonth save(BeginningMonth beginningMonth);
    void delete(BeginningMonth beginningMonth);
}
