package open.api.coc.clans.schedule;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.service.ClanWarService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClanWarScheduler {

    private final ClanWarService clanWarService;

    /**
     * 1분마다 클랜전이 종료되었으나 수집되지 않은 기록을 수집한다.
     */
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void raidScheduling() {
        clanWarService.collectCurrentClanWar();
    }
}
