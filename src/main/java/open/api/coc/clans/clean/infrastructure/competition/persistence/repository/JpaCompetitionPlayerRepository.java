package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompetitionPlayerRepository extends JpaRepository<CompetitionPlayerEntity, String> {
}
