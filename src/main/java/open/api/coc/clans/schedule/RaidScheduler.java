package open.api.coc.clans.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.service.RaidService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RaidScheduler {
    private final RaidService raidService;

    @Scheduled(cron = "0 0 17 ? * MON")
    public void raidScheduling() {
        raidService.saveFinishedRaidInfo();
    }
}
