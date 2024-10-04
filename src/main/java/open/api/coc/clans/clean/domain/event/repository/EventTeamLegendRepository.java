package open.api.coc.clans.clean.domain.event.repository;

import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;

public interface EventTeamLegendRepository {

    LocalDateTime findLatestStartDate();

    EventTeamLegend findByStartDate(LocalDateTime startDate);

}
