package open.api.coc.clans.clean.domain.competition.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClanRoaster;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionParticipateClanRoasterRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterPK;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionClanRoasterMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionParticipateClanService {

    private final CompetitionParticipateClanRoasterRepository competitionParticipateClanRoasterRepository;
    private final CompetitionClanRoasterMapper competitionClanRoasterMapper;

    public void save(CompetitionClanRoaster competitionClanRoaster) {
        CompetitionClanRoasterEntity entity = competitionClanRoasterMapper.toEntity(competitionClanRoaster);
        competitionParticipateClanRoasterRepository.save(entity);
    }

    public List<CompetitionClanRoaster> findAll(Long compClanId) {
        List<CompetitionClanRoasterEntity> competitionClanEntities = competitionParticipateClanRoasterRepository.findAll(compClanId);
        return competitionClanEntities.stream()
                                      .map(competitionClanRoasterMapper::toDomain)
                                      .toList();
    }

    public void removeById(CompetitionClanRoasterPK id) {
        competitionParticipateClanRoasterRepository.deleteById(id);
    }
}
