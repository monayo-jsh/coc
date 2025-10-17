package open.api.coc.clans.schedule;

import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.player.PlayerUseCase;
import open.api.coc.clans.clean.infrastructure.season.repository.JpaSeasonEndManagementQueryRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.database.repository.player.PlayerQueryRepository;
import open.api.coc.clans.schedule.handler.CollectionHandler;
import open.api.coc.clans.service.PlayersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerScheduler {

    private final JpaSeasonEndManagementQueryRepository jpaSeasonEndManagementCustomRepository;

    private final PlayerQueryRepository playerQueryRepository;
    private final PlayersService playersService;

    private final PlayerUseCase playerUseCase;

    private final CollectionHandler collectionHandler;

    /**
     * 매달 4번째 주 월요일 또는 지정된 시즌 종료일에 시즌 초기화.
     */
    @Scheduled(cron = "${scheduling.reset.season}")  // 매주 월요일 14시 1분에 초기화 실행
    @Transactional
    public void resetSeasonData() {
        LocalDate now = LocalDate.now();
        LocalDate fourthMonday = now.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.MONDAY));

        LocalDate seasonEndDate = jpaSeasonEndManagementCustomRepository.findSeasonEndDateByBaseDate(now)
                                                                        .orElse(null);

        if (seasonEndDate != null) {
            // 당월 시즌 종료일이 설정된 경우 설정된 시즌 종료일로 동작하도록 수정
            fourthMonday = seasonEndDate;
        }

        // 현재 날짜가 4번째 주 월요일인지 확인
        if (now.isEqual(fourthMonday)) {
            // 모든 플레이어의 지원/지원받은 유닛 초기화
            long resetPlayerDonationCount = playerQueryRepository.resetAllPlayerDonations();
            log.info("Season reset player donation : {}, completed on {}", resetPlayerDonationCount, LocalDateTime.now());
        }
    }

    // 서버 기동 5초 후 실행
    @Scheduled(initialDelayString = "${scheduling.collect.player.sync.delay}", fixedDelayString = "${scheduling.collect.player.sync.milli-sec}")
    public void syncPlayers() {

        // 시즌 초기화 시 데이터 보정을 위해 지정된 시간에는 수집하지 않음.
        if (collectionHandler.isNotCollectionTime()) {
            log.info("not synchronized players on time: {}", LocalDateTime.now());
            return;
        }

        long startTime = System.currentTimeMillis();
        processSyncPlayers();
        long endTime = System.currentTimeMillis();
        log.info("synchronized players elapsed time : {}", (double) (endTime - startTime) / 1000);
    }

    private void processSyncPlayers() {

        // 플레이어 기록 설정되지 않은 플레이어를 대상으로 갱신
        List<PlayerEntity> players = playersService.findAllWithoutRecordTarget();

        final int offset = 50;
        for (int fromIndex = 0; fromIndex < players.size(); fromIndex += offset) {
            List<PlayerEntity> syncPlayers = players.subList(fromIndex, Math.min(fromIndex + offset, players.size()));
            syncPlayers.stream()
                       .parallel()
                       .forEach(player -> playerUseCase.synchronizePlayerFromSchedule("processSyncPlayers", player.getPlayerTag()));
        }

    }

}
