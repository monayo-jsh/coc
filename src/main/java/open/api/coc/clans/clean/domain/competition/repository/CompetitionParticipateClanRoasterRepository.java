package open.api.coc.clans.clean.domain.competition.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterPK;

public interface CompetitionParticipateClanRoasterRepository {

    void save(CompetitionClanRoasterEntity entity);
    void deleteById(CompetitionClanRoasterPK id);

    List<CompetitionClanRoasterEntity> findAll(Long compClanId);

}
