package open.api.coc.clans.domain.clans.converter;

import java.time.LocalDate;

public interface TimeConverter {
    long toEpochMilliSecond(String time);
    LocalDate toLocalDate(long epochMilli);
}
