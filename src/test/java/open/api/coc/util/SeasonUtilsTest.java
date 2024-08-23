package open.api.coc.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
}