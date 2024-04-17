package open.api.coc.clans.domain.clans.converter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCurrentWarRes;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ClanCurrentWarResConverter implements Converter<ClanWar, ClanCurrentWarRes> {

    private final ClanWarResConverter clanWarResConverter;

    @Override
    public ClanCurrentWarRes convert(ClanWar source) {
        return ClanCurrentWarRes.builder()
                                .state(source.getState())
                                .teamSize(source.getTeamSize())
                                .attacksPerMember(source.getAttacksPerMember())
                                .startTime(toEpochMilliSecond(source.getStartTime()))
                                .endTime(toEpochMilliSecond(source.getEndTime()))
                                .clan(clanWarResConverter.convert(source.getClan()))
                                .opponent(clanWarResConverter.convert(source.getOpponent()))
                                .build();
    }

    private long toEpochMilliSecond(String time) {
        if (StringUtils.isEmpty(time)) return 0;

        final String TIME_PATTERN = "yyyyMMdd'T'HHmmss.SSSX";
        return ZonedDateTime.parse(time, DateTimeFormatter.ofPattern(TIME_PATTERN))
                            .toInstant()
                            .toEpochMilli();
    }

}