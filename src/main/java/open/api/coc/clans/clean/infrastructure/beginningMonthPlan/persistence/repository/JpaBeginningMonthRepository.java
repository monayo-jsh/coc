package open.api.coc.clans.clean.infrastructure.beginningMonthPlan.persistence.repository;

import java.time.LocalDate;
import java.util.Optional;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBeginningMonthRepository extends JpaRepository<BeginningMonth, Long> {

    Optional<BeginningMonth> findByMonth(LocalDate month);

}
