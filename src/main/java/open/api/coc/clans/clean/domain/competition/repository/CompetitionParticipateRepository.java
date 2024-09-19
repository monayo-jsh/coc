package open.api.coc.clans.clean.domain.competition.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.competition.persistence.dto.CompetitionClanDTO;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;

public interface CompetitionParticipateRepository {

    CompetitionClanEntity save(CompetitionClanEntity competitionClanEntity);
//    Optional<CompetitionClanEntity> findByCompetitionId(Long competitionId);

    List<CompetitionClanDTO> findWithClanNameByCompId(Long compId);

}
