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

    @Scheduled(cron = "0 0,5,10 16 ? * MON")
    public void raidScheduling() {
        log.info("clan capital auto collection start");
        raidService.collectClanCapitalRaidSeason();
        log.info("clan capital auto collection ended");
    }

}
