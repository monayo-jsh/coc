package open.api.coc.clans.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.raid.RaidUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClanCapitalScheduler {
    private final RaidUseCase raidUseCase;

    // 매주 월요일 오후 4시 0분부터 5분 간격으로 클랜 캐피탈 종료 데이터 수집
    @Scheduled(cron = "0 0/5 16 ? * MON")
    public void raidScheduling() {
        raidUseCase.collectCapitalCurrentSeason();
    }

}
