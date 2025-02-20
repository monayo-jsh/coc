package open.api.coc.clans.clean.infrastructure.beginningMonthPlan.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.beginningMonthPlan.model.BeginningMonth;
import open.api.coc.clans.clean.domain.beginningMonthPlan.repository.BeginningMonthRepository;
import open.api.coc.clans.clean.infrastructure.beginningMonthPlan.persistence.repository.JpaBeginningMonthRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BeginningMonthDatabaseService implements BeginningMonthRepository {

    private final JpaBeginningMonthRepository repository;

    @Override
    public List<BeginningMonth> findAll(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "month"));
        return repository.findAllByLimit(pageable);
    }

    @Override
    public BeginningMonth findLatest() {
        return repository.findLatest();
    }

    @Override
    public Optional<BeginningMonth> findByMonth(LocalDate month) {
        return repository.findByMonth(month);
    }

    @Override
    public BeginningMonth save(BeginningMonth beginningMonth) {
        return repository.save(beginningMonth);
    }

}
