package open.api.coc.clans.clean.domain.competition.repository;

import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;

public interface CompetitionParticipateRepository {

    CompetitionClanEntity save(CompetitionClanEntity competitionClanEntity);
//    Optional<CompetitionClanEntity> findByCompetitionId(Long competitionId);


}
