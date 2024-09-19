package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionParticipateRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.dto.CompetitionClanDTO;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompetitionParticipateCoreRepository implements CompetitionParticipateRepository {

    private final JpaCompetitionClanRepository jpaCompetitionClanRepository;
    private final JpaCompetitionClanCustomRepository jpaCompetitionClanCustomRepository;
    @Override
    public CompetitionClanEntity save(CompetitionClanEntity entity) {
        return jpaCompetitionClanRepository.save(entity);
    }

    @Override
    public List<CompetitionClanDTO> findWithClanNameByCompId(Long compId) {
        return jpaCompetitionClanCustomRepository.findWithClanNameByCompId(compId);
    }

}
