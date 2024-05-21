package open.api.coc.clans.database.entity.raid.converter;

import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonMemberResponse;
import open.api.coc.clans.domain.clans.ClanCapitalRaidSeasonResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RaiderEntityConverter implements Converter<ClanCapitalRaidSeasonMemberResponse, RaiderEntity> {

    @Override
    public RaiderEntity convert(ClanCapitalRaidSeasonMemberResponse source) {
        return RaiderEntity.builder()
                .tag(source.getTag())
                .name(source.getName())
                .resourceLooted(source.getCapitalResourcesLooted()).build();
    }
}
