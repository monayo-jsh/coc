package open.api.coc.clans.database.entity.league.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.database.entity.common.converter.IconUrlEntityConverter;
import open.api.coc.clans.database.entity.league.LeagueEntity;
import open.api.coc.external.coc.clan.domain.common.Label;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeagueEntityConverter implements Converter<Label, LeagueEntity> {

    private final IconUrlEntityConverter iconUrlEntityConverter;

    @Override
    public @NonNull LeagueEntity convert(Label source) {
        return LeagueEntity.builder()
                           .id(source.getId())
                           .name(source.getName())
                           .iconUrl(iconUrlEntityConverter.convert(source.getIconUrls()))
                           .build();
    }

}
