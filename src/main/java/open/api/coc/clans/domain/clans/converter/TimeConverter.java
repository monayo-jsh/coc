package open.api.coc.clans.domain.clans.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TimeConverter {
    long toEpochMilliSecond(String time);
    long toEpochMilliSecond(LocalDate localDate);
    long toEpochMilliSecond(LocalDateTime localDateTime);
    LocalDate toLocalDate(long epochMilli);
    LocalDateTime toLocalDateTime(long epochMilli);
}
