package open.api.coc.clans.clean.domain.event.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.event.exception.EventTeamLegendNotExistsException;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventTeamLegendService {

    private final EventTeamLegendRepository teamLegendRepository;

    @Transactional(readOnly = true)
    public EventTeamLegend findLatestTeamLegend() {
        LocalDateTime startDate = teamLegendRepository.findLatestStartDate();
        if (startDate == null) {
            throw new EventTeamLegendNotExistsException();
        }

        return teamLegendRepository.findByStartDate(startDate);
    }

    @Transactional
    public void update(EventTeamLegend teamLegend) {
        teamLegendRepository.save(teamLegend);
    }
}