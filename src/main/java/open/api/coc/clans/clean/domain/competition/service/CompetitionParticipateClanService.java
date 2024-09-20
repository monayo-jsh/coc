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

    public void create(CompetitionClanRoaster competitionClanRoaster) {
        CompetitionClanRoasterEntity entity = competitionClanRoasterMapper.toEntity(competitionClanRoaster);
        competitionParticipateClanRoasterRepository.save(entity);
    }

    public List<CompetitionClanRoaster> findAllByCompClanId(Long compClanId) {
        List<CompetitionClanRoasterEntity> competitionClanEntities = competitionParticipateClanRoasterRepository.findAllByCompClanId(compClanId);
        return competitionClanEntities.stream()
                                      .map(competitionClanRoasterMapper::toDomain)
                                      .toList();
    }

    public void remove(Long compClanId, String playerTag) {
        CompetitionClanRoasterPK competitionClanRoasterPK = CompetitionClanRoasterPK.create(compClanId, playerTag);
        competitionParticipateClanRoasterRepository.deleteById(competitionClanRoasterPK);
    }
}
