package open.api.coc.clans.domain.clans.converter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDateTime withMaxTime(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX.withNano(999_999_000));
    }
}
