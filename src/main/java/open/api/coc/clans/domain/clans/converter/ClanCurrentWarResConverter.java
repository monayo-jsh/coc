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
    private final TimeConverter timeConverter;

    @Override
    public ClanCurrentWarRes convert(ClanWar source) {
        return ClanCurrentWarRes.builder()
                                .state(source.getState())
                                .teamSize(source.getTeamSize())
                                .attacksPerMember(source.getAttacksPerMember())
                                .startTime(timeConverter.toEpochMilliSecond(source.getStartTime()))
                                .endTime(timeConverter.toEpochMilliSecond(source.getEndTime()))
                                .clan(clanWarResConverter.convert(source.getClan()))
                                .opponent(clanWarResConverter.convert(source.getOpponent()))
                                .build();
    }
}