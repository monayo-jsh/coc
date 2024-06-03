package open.api.coc.clans.domain.clans.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCurrentWarResponse;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanCurrentWarResConverter implements Converter<ClanWar, ClanCurrentWarResponse> {

    private final ClanWarResponseConverter clanWarResConverter;
    private final TimeConverter timeConverter;

    @Override
    public ClanCurrentWarResponse convert(ClanWar source) {
        return ClanCurrentWarResponse.builder()
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