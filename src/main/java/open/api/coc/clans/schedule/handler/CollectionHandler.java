package open.api.coc.clans.schedule.handler;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.season.repository.JpaSeasonEndManagementQueryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectionHandler {

    private final JpaSeasonEndManagementQueryRepository jpaSeasonEndManagementCustomRepository;

    public boolean isNotCollectionTime() {
        // 시즌 초기화는 매달 4번째주 월요일 초기화를 기준으로 함.
        LocalDate now = LocalDate.now();
        LocalDate fourthMonday = now.with(TemporalAdjusters.dayOfWeekInMonth(4, DayOfWeek.MONDAY));

        LocalDate seasonEndDate = jpaSeasonEndManagementCustomRepository.findSeasonEndDateByBaseDate(now)
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
