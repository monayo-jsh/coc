package open.api.coc.clans.clean.infrastructure.beginningMonthPlan.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaBeginningMonthRepository extends JpaRepository<BeginningMonth, Long> {

    @Query(
        value = """
            select beginning_month
            from BeginningMonth beginning_month
            join fetch beginning_month.plans
        """
    )
    List<BeginningMonth> findAllByLimit(Pageable pageable);

    @Query(
        value = """
            select beginning_month
            from BeginningMonth beginning_month
            join fetch beginning_month.plans
            order by beginning_month.month desc
        """
    )
    BeginningMonth findLatest();

    Optional<BeginningMonth> findByMonth(LocalDate month);

}
