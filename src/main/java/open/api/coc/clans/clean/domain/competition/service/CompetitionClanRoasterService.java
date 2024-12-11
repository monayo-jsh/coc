package open.api.coc.clans.clean.domain.competition.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClanRoaster;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionClanRoasterRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterPK;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionClanRoasterMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionClanRoasterService {

    private final CompetitionClanRoasterRepository clanRoasterRepository;
    private final CompetitionClanRoasterMapper clanRoasterMapper;

    public void create(CompetitionClanRoaster clanRoaster) {
        CompetitionClanRoasterEntity entity = clanRoasterMapper.toEntity(clanRoaster);
        clanRoasterRepository.save(entity);
    }

    public List<CompetitionClanRoaster> findAllByCompClanId(Long compClanId) {
        List<CompetitionClanRoasterEntity> clanRoasterEntities = clanRoasterRepository.findAllByCompClanId(compClanId);
        return clanRoasterEntities.stream()
                                      .map(clanRoasterMapper::toDomain)
                                      .toList();
    }

    public void remove(Long compClanId, String playerTag) {
        CompetitionClanRoasterPK clanRoasterPK = CompetitionClanRoasterPK.create(compClanId, playerTag);
        clanRoasterRepository.deleteById(clanRoasterPK);
    }
}
