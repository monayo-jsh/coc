package open.api.coc.clans.clean.infrastructure.event.persistence.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendRepository;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.mapper.EventEntityMapper;
import open.api.coc.clans.clean.infrastructure.event.persistence.repository.JpaEventCustomRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventTeamLegendDatabaseService implements EventTeamLegendRepository {

    private final JpaEventCustomRepository eventCustomRepository;
    private final EventEntityMapper eventEntityMapper;

    @Override
    public LocalDateTime findLatestStartDate() {
        return eventCustomRepository.findLatestStartDate();
    }

    @Override
    public EventTeamLegend findByStartDate(LocalDateTime startDate) {
        EventEntity teamLegendEntity = eventCustomRepository.findByStartDate(startDate);
        return eventEntityMapper.toEventTeamLegend(teamLegendEntity);
    }
}
