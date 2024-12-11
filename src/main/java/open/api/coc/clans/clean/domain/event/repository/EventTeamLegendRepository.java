package open.api.coc.clans.clean.domain.event.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;

public interface EventTeamLegendRepository {

    LocalDateTime findLatestStartDate();

    Optional<EventTeamLegend> findById(Long eventId);
    Optional<EventTeamLegend> findByStartDate(LocalDateTime startDate);

    void save(EventTeamLegend teamLegend);

}
