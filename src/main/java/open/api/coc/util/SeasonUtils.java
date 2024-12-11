package open.api.coc.util;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;

public class SeasonUtils {

    /**
     * 시즌 종료일시 구하기
     *
     * @param year 연도
     * @param month 월
     * @return 시즌 종료일시
     */
    public static LocalDateTime getSeasonEndTime(int year, int month) {
        // 해당 월의 첫 번째 날짜를 구합니다.
        LocalDate firstOfMonth = LocalDate.of(year, month, 1);

        // 첫 번째 월요일을 찾습니다.
        LocalDate firstMonday = firstOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

        // 4번째 주의 월요일을 계산합니다.
        LocalDate fourthMonday = firstMonday.plusWeeks(3);

        // 해당 일의 오후 2시로 설정합니다.
        return withSeasonEndTime(fourthMonday);
    }

    public static LocalDateTime withSeasonEndTime(LocalDate endDate) {
        return LocalDateTime.of(endDate, LocalTime.of(14, 0, 0, 0));
    }

    /**
     * 두 LocalDateTime 간의 차이를 "X개월 Y일 Z시간 전" 형식으로 포맷합니다.
     *
     * @param startDateTime 시작일시
     * @param endDateTime 종료일시
     * @return 포맷된 차이 문자열
     */
    public static LocalDateTime getDifferenceLocalDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Period로 년, 월, 일 차이 계산
        Period period = Period.between(startDateTime.toLocalDate(), endDateTime.toLocalDate());

        // Duration으로 시간, 분, 초 차이 계산
        Duration duration = Duration.between(startDateTime.toLocalTime(), endDateTime.toLocalTime());

        // Period에서 차이 값을 가져옴
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();

        // Duration에서 차이 값을 가져옴
        int hours = (int) (duration.toHours());
        int minutes = (int) (duration.toMinutes() % 60);
        int seconds = (int) (duration.getSeconds() % 60);

        return LocalDateTime.of(LocalDate.of(years, months, days), LocalTime.of(hours, minutes, seconds, 0));
    }
}
