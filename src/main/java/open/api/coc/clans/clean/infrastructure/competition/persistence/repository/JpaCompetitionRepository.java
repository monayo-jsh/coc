package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.time.LocalDate;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompetitionRepository extends JpaRepository<CompetitionEntity, Long> {

    boolean existsByNameAndStartDateAndEndDate(String name, LocalDate startDate, LocalDate endDate);

}
