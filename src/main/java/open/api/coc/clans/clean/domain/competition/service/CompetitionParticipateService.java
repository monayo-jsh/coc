package open.api.coc.clans.clean.domain.competition.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionParticipateRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.dto.CompetitionClanDTO;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionClanMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompetitionParticipateService {

    private final CompetitionParticipateRepository competitionClanRepository;
    private final CompetitionClanMapper competitionClanMapper;

    public CompetitionClan save(CompetitionClan competitionClan) {
        CompetitionClanEntity entity = competitionClanMapper.toEntity(competitionClan);
        CompetitionClanEntity saveEntity = competitionClanRepository.save(entity);
        return competitionClanMapper.toDomain(saveEntity);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public List<CompetitionClan> findWithClanNameByCompId(Long compId) {
        List<CompetitionClanDTO> competitionClanEntities = competitionClanRepository.findWithClanNameByCompId(compId);
        return competitionClanEntities.stream()
                                      .map(competitionClanMapper::toDomain)
                                      .toList();
    }
}
