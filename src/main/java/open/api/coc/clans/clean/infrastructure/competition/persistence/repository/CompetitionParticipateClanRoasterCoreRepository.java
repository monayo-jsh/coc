package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionParticipateClanRoasterRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterPK;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompetitionParticipateClanRoasterCoreRepository implements CompetitionParticipateClanRoasterRepository {

    private final JpaCompetitionClanRoasterRepository jpaCompetitionClanRoasterRepository;

    @Override
    public void save(CompetitionClanRoasterEntity entity) {
        jpaCompetitionClanRoasterRepository.save(entity);
    }

    @Override
    public void deleteById(CompetitionClanRoasterPK id) {
        jpaCompetitionClanRoasterRepository.deleteById(id);
    }

    @Override
    public List<CompetitionClanRoasterEntity> findAllByCompClanId(Long compClanId) {
        if (compClanId == null) {
            throw new IllegalArgumentException("compClanId can not be null");
        }
        return jpaCompetitionClanRoasterRepository.findAllByIdCompClanId(compClanId);
    }
}
