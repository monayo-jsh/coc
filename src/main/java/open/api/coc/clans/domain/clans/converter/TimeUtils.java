package open.api.coc.clans.domain.clans.converter;

import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TimeUtils implements TimeConverter{

    private static final String TIME_PATTERN = "yyyyMMdd'T'HHmmss.SSSX";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    @Override
    public long toEpochMilliSecond(String time) {
        if (StringUtils.isEmpty(time)) return 0;

        return ZonedDateTime.parse(time, FORMATTER)
                .toInstant()
                .toEpochMilli();
    }

}
