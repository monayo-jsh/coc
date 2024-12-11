package open.api.coc.clans.clean.domain.competition.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;

public interface CompetitionRepository {

    CompetitionEntity save(CompetitionEntity competitionEntity);
    Optional<CompetitionEntity> findById(Long id);
    List<CompetitionEntity> findAll();

    boolean exists(String name, LocalDate startDate, LocalDate endDate);
}
