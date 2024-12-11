package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompetitionCoreRepository implements CompetitionRepository {

    private final JpaCompetitionRepository jpaCompetitionRepository;
    private final JpaCompetitionCustomRepository jpaCompetitionCustomRepository;
    @Override
    public CompetitionEntity save(CompetitionEntity competitionEntity) {
        return jpaCompetitionRepository.save(competitionEntity);
    }

    @Override
    public Optional<CompetitionEntity> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id can not be null");
        }

        return jpaCompetitionCustomRepository.findById(id);
    }

    @Override
    public List<CompetitionEntity> findAll() {
        return jpaCompetitionRepository.findAll();
    }

    @Override
    public boolean exists(String name, LocalDate startDate, LocalDate endDate) {
        return jpaCompetitionRepository.existsByNameAndStartDateAndEndDate(name, startDate, endDate);
    }

}
