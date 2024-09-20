package open.api.coc.clans.clean.domain.competition.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.model.CompetitionClanSchedule;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionClanScheduleRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.mapper.CompetitionClanScheduleMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompetitionClanScheduleService {

    private final CompetitionClanScheduleRepository competitionClanScheduleRepository;
    private final CompetitionClanScheduleMapper competitionClanScheduleMapper;

    public List<CompetitionClanSchedule> findAllByCompClanId(Long compClanId) {
        List<CompetitionClanScheduleEntity> competitionClanScheduleEntities = competitionClanScheduleRepository.findAllByCompClanId(compClanId);
        return competitionClanScheduleEntities.stream()
                                              .map(competitionClanScheduleMapper::toDomain)
                                              .toList();
    }

    public void create(CompetitionClanSchedule competitionClanSchedule) {
        CompetitionClanScheduleEntity entity = competitionClanScheduleMapper.toEntity(competitionClanSchedule);
        competitionClanScheduleRepository.save(entity);
    }

}
