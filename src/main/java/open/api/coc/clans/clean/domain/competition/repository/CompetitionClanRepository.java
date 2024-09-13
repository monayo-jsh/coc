package open.api.coc.clans.clean.domain.competition.repository;

import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;

public interface CompetitionClanRepository {

    CompetitionClanEntity save(CompetitionClanEntity competitionClanEntity);
    Optional<CompetitionClanEntity> findByCompetitionId(Long competitionId);


}
