package open.api.coc.clans.domain.clans.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueGroupResponse;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueRoundResponse;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueGroup;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueRound;
import open.api.coc.external.coc.clan.domain.clan.ClanWarLeague;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ClanCurrentWarLeagueGroupResponseConverter implements Converter<ClanCurrentWarLeagueGroup, ClanCurrentWarLeagueGroupResponse> {

    private final ClanResponseConverter clanResponseConverter;
    private final ClanCurrentWarLeagueRoundResponseConverter clanCurrentWarLeagueRoundResponseConverter;

    @Override
    public ClanCurrentWarLeagueGroupResponse convert(ClanCurrentWarLeagueGroup source) {
        return ClanCurrentWarLeagueGroupResponse.builder()
                                                .state(source.getState())
                                                .season(source.getSeason())
                                                .clans(makeClan(source.getClans()))
                                                .rounds(makeLeagueRounds(source.getRounds()))
                                                .build();
    }

    private List<ClanCurrentWarLeagueRoundResponse> makeLeagueRounds(List<ClanCurrentWarLeagueRound> rounds) {
        if (CollectionUtils.isEmpty(rounds)) return Collections.emptyList();

        return rounds.stream()
                     .map(clanCurrentWarLeagueRoundResponseConverter::convert)
                     .collect(Collectors.toList());
    }

    private List<ClanResponse> makeClan(List<ClanWarLeague> clans) {
        if (CollectionUtils.isEmpty(clans)) return Collections.emptyList();

        return clans.stream()
                    .map(clanResponseConverter::convert)
                    .collect(Collectors.toList());
    }
}
