package open.api.coc.clans.clean.domain.competition.repository;

import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;

public interface CompositionClanScheduleRepository {

    CompetitionClanScheduleEntity save(CompetitionClanScheduleEntity competitionClanScheduleEntity);
    Optional<CompetitionClanScheduleEntity> findById(Long id);

}
