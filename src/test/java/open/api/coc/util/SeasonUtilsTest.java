package open.api.coc.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class SeasonUtilsTest {

    @Test
    void test() {

        LocalDateTime now = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime seasonEndDate = SeasonUtils.getSeasonEndTime(now.getYear(), now.getMonth().getValue());
        for (int i=1; i<=31; i++) {
            boolean before = seasonEndDate.isBefore(now);
            System.out.printf("오늘[%s] ===> 시즌 종료일 : %s 시즌 진행중: %s%n", now, seasonEndDate, before);

            now = now.plusDays(1);
        }

    }

    @Test
    void test2() {
        LocalDate today = LocalDate.now();
        LocalDateTime seasonEndDate = SeasonUtils.getSeasonEndTime(today.getYear(), today.getMonth().getValue());

        // 1시 59분
        LocalDateTime before = seasonEndDate.withHour(13).withMinute(59);
        System.out.printf("%s < %s ==> %s%n", before, seasonEndDate, before.isBefore(seasonEndDate) ? seasonEndDate : null);

        LocalDateTime nextSeasonEndDate = seasonEndDate.plusMonths(1);

        // 2시 0분
        LocalDateTime same = seasonEndDate.withHour(14).withMinute(0);
        System.out.printf("%s < %s ==> %s%n", same, seasonEndDate, same.isBefore(seasonEndDate) ? seasonEndDate : nextSeasonEndDate);

        // 2시 1분
        LocalDateTime after = seasonEndDate.withHour(14).withMinute(1);
        System.out.printf("%s < %s ==> %s%n", after, seasonEndDate, after.isBefore(seasonEndDate) ? seasonEndDate : nextSeasonEndDate);

    }

    @Test
    void checkTime() {
        LocalTime test = LocalTime.of(13, 59, 59);
        System.out.printf("%s 범위에 포함 여부: %s\n", test, checkPeriod2PMTime(test));

        test = LocalTime.of(14, 0, 0);
        System.out.printf("%s 범위에 포함 여부: %s\n", test, checkPeriod2PMTime(test));

        test = LocalTime.of(14, 0, 1);
        System.out.printf("%s 범위에 포함 여부: %s\n", test, checkPeriod2PMTime(test));

        test = LocalTime.of(14, 1, 0);
        System.out.printf("%s 범위에 포함 여부: %s\n", test, checkPeriod2PMTime(test));

        test = LocalTime.of(14, 2, 0);
        System.out.printf("%s 범위에 포함 여부: %s\n", test, checkPeriod2PMTime(test));
    }

    private boolean checkPeriod2PMTime(LocalTime now) {
        LocalTime startTime = LocalTime.of(14, 0, 0);
        LocalTime endTime = LocalTime.of(14, 2, 0);

        // 현재 시간이 시작 시간보다 이전인 경우
        if (now.isBefore(startTime)) {
            return false;
        }

        // 현재 시간이 종료 시간보다 이전인 경우
        if (now.isBefore(endTime)) {
            return true;
        }

        // 현재 시간이 시작 시간보다 이후이고 종료 시간이 종료 시간보다 이후인 경우
        return false;
    }

    @Test
    void checkDayOfWeek() {

        LocalDate now = LocalDate.now();

        System.out.printf("%s 는 월요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.MONDAY));
        System.out.printf("%s 는 화요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.TUESDAY));
        System.out.printf("%s 는 수요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.WEDNESDAY));
        System.out.printf("%s 는 목요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.THURSDAY));
        System.out.printf("%s 는 금요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.FRIDAY));
        System.out.printf("%s 는 토요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.SATURDAY));
        System.out.printf("%s 는 일요일입니까 ? %s\n", now, now.getDayOfWeek().equals(DayOfWeek.SUNDAY));

    }
}