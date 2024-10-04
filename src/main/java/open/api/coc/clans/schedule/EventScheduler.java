package open.api.coc.clans.schedule;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.event.EventUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final EventUseCase eventUseCase;

    @Scheduled(fixedDelay = 1000 * 60)
    public void processTeamLegendRecord() {
        eventUseCase.processTeamLegendRecord();
    }

}
