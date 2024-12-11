package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompetitionClanRepository extends JpaRepository<CompetitionClanEntity, Long> {
}
