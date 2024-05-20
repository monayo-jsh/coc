package open.api.coc.clans.domain.clans.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.ClanContentEntity;
import open.api.coc.clans.domain.clans.ClanContentResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanContentResponseConverter implements Converter<ClanContentEntity, ClanContentResponse> {

    @Override
    public ClanContentResponse convert(ClanContentEntity source) {
        return ClanContentResponse.builder()
                                  .tag(source.getTag())
                                  .clanWarYn(source.getClanWarYn())
                                  .clanWarLeagueYn(source.getWarLeagueYn())
                                  .clanCapitalYn(source.getClanCapitalYn())
                                  .clanWarParallelYn(source.getClanWarParallelYn())
                                  .build();
    }

}
