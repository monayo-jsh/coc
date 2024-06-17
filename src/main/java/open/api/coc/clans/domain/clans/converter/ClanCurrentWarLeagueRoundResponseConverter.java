package open.api.coc.clans.domain.clans.converter;

import java.util.ArrayList;
import open.api.coc.clans.domain.clans.ClanCurrentWarLeagueRoundResponse;
import open.api.coc.external.coc.clan.domain.clan.ClanCurrentWarLeagueRound;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClanCurrentWarLeagueRoundResponseConverter implements Converter<ClanCurrentWarLeagueRound, ClanCurrentWarLeagueRoundResponse> {

    @Override
    public ClanCurrentWarLeagueRoundResponse convert(ClanCurrentWarLeagueRound source) {
        return ClanCurrentWarLeagueRoundResponse.builder()
                                                .warTags(new ArrayList<>(source.getWarTags()))
                                                .build();
    }

}
