package open.api.coc.clans.clean.infrastructure.season.repository;

import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.season.repository.SeasonRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SeasonDatabaseService implements SeasonRepository {

    private final JpaSeasonEndManagementCustomRepository jpaSeasonEndManagementCustomRepository;

    @Override
    public Optional<LocalDate> findSeasonEndDateByBaseDate(LocalDate baseDate) {
        return jpaSeasonEndManagementCustomRepository.findSeasonEndDateByBaseDate(baseDate);
    }
}
