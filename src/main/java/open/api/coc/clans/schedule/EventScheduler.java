package open.api.coc.clans.schedule;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.event.EventUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final EventUseCase eventUseCase;

    @Scheduled(fixedDelay = 1000)
    public void processForTeamLegendRecord() {
        // 팀 전설내기 분 단위 기록 갱신용
        eventUseCase.processForTeamLegendRecord();
    }

    @Scheduled(cron = "0 59 13 ? * *")
    public void processForTeamLegendRecordKeeping() {
        // 팀 전설내기 일별 기록 보관용 스케쥴러
        eventUseCase.processForTeamLegendRecordKeeping();
    }
}
