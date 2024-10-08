package open.api.coc.clans.schedule;

import jakarta.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.infrastructure.season.repository.JpaSeasonEndManagementCustomRepository;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.repository.player.PlayerQueryRepository;
import open.api.coc.clans.service.PlayersService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlayerScheduler {

    private final JpaSeasonEndManagementCustomRepository jpaSeasonEndManagementCustomRepository;

    private final PlayerQueryRepository playerQueryRepository;
    private final PlayersService playersService;

    /**
     * 매달 4번째 주 월요일에 시즌 초기화.
     */
    @Scheduled(cron = "0 0 14 ? * MON")  // 매주 월요일 14시에 실행
    @Transactional
    public void resetSeasonData() {
        LocalDate now = LocalDate.now();
        LocalDate fourthMonday = now.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.MONDAY));

        LocalDate seasonEndDate = jpaSeasonEndManagementCustomRepository.findSeasonEndDateBy(now)
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

    @Scheduled(cron = "0/10 * * * * *")  // 매 10초마다
    public void processForPlayerRecordKeeping() {
        List<String> playerTags = playersService.findAllPlayersToRecord();
        if (playerTags.isEmpty()) return;
        for(String playerTag : playerTags) {
            try {
                playersService.updatePlayer(playerTag);
            } catch (Exception e) {
                log.error("플레이어 기록 보관 실패: %s".formatted(playerTag), e);
            }
        }
    }

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void syncPlayers() {

        // 시즌 초기화 시 데이터 보정을 위해 지정된 시간에는 수집하지 않음.
        if (isNotCollectionTime()) {
            log.info("not collection players on : {}", LocalDateTime.now());
            return;
        }

        log.info("collection players start");
        long before = System.nanoTime();
        processSyncPlayers();
        log.info("collection players ended");
        long after = System.nanoTime();
        log.info("collection elapsed time : {}", (double) (after - before) / 1_000_000_000);
    }

    private void processSyncPlayers() {

        List<PlayerEntity> players = playersService.findAllPlayerEntities();

        final int offset = 50;
        for (int fromIndex = 0; fromIndex < players.size(); fromIndex += offset) {
            List<PlayerEntity> syncPlayers = players.subList(fromIndex, Math.min(fromIndex + offset, players.size()));
            syncPlayers.stream()
                       .parallel()
                       .map(player -> {
                           try {
                               playersService.updatePlayer(player.getPlayerTag());
                               return true;
                           } catch (Exception e) {
                               log.error("player {} is update failed...", player.getName());
                               log.error("player {} is fail message", e.getMessage());
                               return false;
                           }
                       })
                       .collect(Collectors.toList());
        }

    }

    private boolean isNotCollectionTime() {
        // 시즌 초기화는 매달 4번째주 월요일 초기화를 기준으로 함.
        LocalDate now = LocalDate.now();
        LocalDate fourthMonday = now.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.MONDAY));

        LocalDate seasonEndDate = jpaSeasonEndManagementCustomRepository.findSeasonEndDateBy(now)
                                                                        .orElse(null);

        if (seasonEndDate != null) {
            // 당월 시즌 종료일이 설정된 경우 설정된 시즌 종료일로 동작하도록 수정
            fourthMonday = seasonEndDate;
        }

        if (!now.isEqual(fourthMonday)) {
            // 4번째주 월요일이 아니면 수집 진행
            return false;
        }

        LocalTime time = LocalTime.now();
        LocalTime startTime = LocalTime.of(14, 0, 0); // 수집 제외 시작 시간
        LocalTime endTime = LocalTime.of(15, 0, 0); // 수집 제외 종료 시간

        // 수집 시간이 아닌 경우 (지정된 시간 범위 내에 있으면 수집 제외)
        if (isWithTimeRange(time, startTime, endTime)) {
            return true;
        }

        // 그 외는 수집 진행
        return false;
    }

    boolean isWithTimeRange(LocalTime now, LocalTime startTime, LocalTime endTime) {
        // 현재 시간이 시작 시간보다 이전인 경우 : 범위 밖이므로 false
        if (now.isBefore(startTime)) {
            return false;
        }

        // 현재 시간이 종료 시간보다 이전인 경우 : 범위 내이므로 true
        if (now.isBefore(endTime)) {
            return true;
        }

        // 현재 시간이 시작 시간보다 이후이고 종료 시간이 종료 시간보다 이후인 경우 : 범위 밖이므로 false
        return false;
    }
}
