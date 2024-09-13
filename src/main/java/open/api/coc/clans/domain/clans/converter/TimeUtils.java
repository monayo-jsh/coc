package open.api.coc.clans.domain.clans.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
public class TimeUtils implements TimeConverter {

    private static final String TIME_PATTERN = "yyyyMMdd'T'HHmmss.SSSX";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    @Override
    public long toEpochMilliSecond(String time) {
        if (StringUtils.isEmpty(time)) return 0;

        return ZonedDateTime.parse(time, FORMATTER)
                .toInstant()
                .toEpochMilli();
    }

    @Override
    public long toEpochMilliSecond(LocalDate localDate) {
        return localDate.toEpochSecond(LocalTime.MIN, OffsetDateTime.now().getOffset()) * 1000;
    }

    @Override
    public long toEpochMilliSecond(LocalDateTime localDate) {
        return localDate.toEpochSecond(OffsetDateTime.now().getOffset()) * 1000;
    }

    @Override
    public LocalDate toLocalDate(long epochMilli) {
        return getZonedDateTime(epochMilli).toLocalDate();
    }

    @Override
    public LocalDateTime toLocalDateTime(long epochMilli) {
        return getZonedDateTime(epochMilli).toLocalDateTime();
    }

    private ZonedDateTime getZonedDateTime(long epochMilli) {
        return Instant.ofEpochMilli(epochMilli).atZone(ZoneId.of("Asia/Seoul"));
    }

    public static LocalDateTime withMinTime(LocalDate date) {
        // LocalDate 시간을 00:00:00 설정하여 반환
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDateTime withMaxTime(LocalDate date) {
        // LocalDate 시간을 23:59:59.999999000 설정하여 반환
        return LocalDateTime.of(date, LocalTime.MAX.withNano(999_999_000));
    }

    public static LocalDateTime getDateMinTimeDaysAgo(int daysAgo) {
        LocalDate date = LocalDate.now().minusDays(daysAgo);
        return withMinTime(date);
    }

    public static LocalDateTime getDateMaxTimeDaysAgo(int daysAgo) {
        LocalDate date = LocalDate.now().minusDays(daysAgo);
        return withMaxTime(date);
    }

    public static LocalDateTime toFirstDayOfMonthDateTime(LocalDate date) {
        LocalDate firstDayOfMonthDate = toFirstDayOfMonth(date);
        return withMinTime(firstDayOfMonthDate);
    }

    private static LocalDate toFirstDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDateTime toLastDayOfMonthDateTime(LocalDate date) {
        LocalDate lastDayOfMonth = toLastDayOfMonth(date);
        return withMaxTime(lastDayOfMonth);
    }

    private static LocalDate toLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static String formattedISODate(LocalDate date) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }
}
