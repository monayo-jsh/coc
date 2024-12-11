package open.api.coc.clans.clean.infrastructure.season.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import open.api.coc.clans.clean.infrastructure.season.entity.SeasonEndManagementEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeasonDatabaseService implements SeasonRepository {

    private final JpaSeasonEndManagementRepository repository;
    private final JpaSeasonEndManagementQueryRepository queryRepository;

    @Override
    public Optional<LocalDate> findSeasonEndDateByBaseDate(LocalDate baseDate) {
        return queryRepository.findSeasonEndDateByBaseDate(baseDate);
    }

    @Override
    public List<LocalDate> findLatestSeasonEndDate(int limit) {
        return queryRepository.findLatestSeasonEndDate(limit);
    }

    @Override
    public void save(LocalDate endDate) {
        SeasonEndManagementEntity entity = SeasonEndManagementEntity.createNew(endDate);
        repository.save(entity);
    }

    @Override
    public void remove(LocalDate endDate) {
        SeasonEndManagementEntity entity = SeasonEndManagementEntity.createNew(endDate);
        entity.markNotNew();
        repository.delete(entity);
    }

}
