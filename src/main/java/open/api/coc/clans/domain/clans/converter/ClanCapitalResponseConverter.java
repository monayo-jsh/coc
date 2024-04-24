package open.api.coc.clans.domain.clans.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.ClanCapitalResponse;
import open.api.coc.external.coc.clan.domain.clan.ClanCapital;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanCapitalResponseConverter implements Converter<ClanCapital, ClanCapitalResponse> {

    @Override
    public ClanCapitalResponse convert(ClanCapital source) {
        return ClanCapitalResponse.builder()
                                  .capitalHallLevel(source.getCapitalHallLevel())
                                  .build();
    }

}