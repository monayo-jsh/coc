package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionParticipateRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompetitionParticipateCoreRepository implements CompetitionParticipateRepository {

    private final JpaCompetitionClanRepository jpaCompetitionClanRepository;

    @Override
    public CompetitionClanEntity save(CompetitionClanEntity entity) {
        return jpaCompetitionClanRepository.save(entity);
    }
}
