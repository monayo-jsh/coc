package open.api.coc.clans.clean.domain.competition.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;

public interface CompetitionClanScheduleRepository {

    void save(CompetitionClanScheduleEntity entity);

    List<CompetitionClanScheduleEntity> findAllByCompClanId(Long compClanId);

}
