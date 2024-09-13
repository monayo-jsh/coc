package open.api.coc.clans.clean.domain.competition.service;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClan;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionParticipateRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionClanMapper;
import org.springframework.stereotype.Service;

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
}
