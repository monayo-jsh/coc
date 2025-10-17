package open.api.coc.clans.schedule;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.event.EventUseCase;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "scheduling.collect.event.team-legend.enabled", havingValue = "true", matchIfMissing = false)
public class EventScheduler {

    private final EventUseCase eventUseCase;

    @Scheduled(fixedDelayString = "${scheduling.collect.event.team-legend.min-record-milli-sec}")
    public void processForTeamLegendRecord() {
        // 팀 전설내기 분 단위 기록 갱신용
        eventUseCase.processForTeamLegendRecord();
    }

    @Scheduled(cron = "${scheduling.collect.event.team-legend.daily-record}")
    public void processForTeamLegendRecordKeeping() {
        // 팀 전설내기 일별 기록 보관용 스케쥴러
        eventUseCase.processForTeamLegendRecordKeeping();
    }
}
