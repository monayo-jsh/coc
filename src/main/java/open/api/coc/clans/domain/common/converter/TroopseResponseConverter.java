package open.api.coc.clans.domain.common.converter;

import open.api.coc.clans.domain.common.TroopsResponse;
import open.api.coc.external.coc.clan.domain.common.Troops;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TroopseResponseConverter implements Converter<Troops, TroopsResponse> {

    @Override
    public TroopsResponse convert(Troops source) {
        return TroopsResponse.builder()
                             .name(source.getName())
                             .level(source.getLevel())
                             .maxLevel(source.getMaxLevel())
                             .village(source.getVillage())
                             .build();
    }

}
