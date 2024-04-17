package open.api.coc.clans.domain.clans.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.LeagueRes;
import open.api.coc.external.coc.clan.domain.clan.League;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeagueResConverter implements Converter<League, LeagueRes> {

    private final IconUrlResConverter iconUrlResConverter;

    @Override
    public LeagueRes convert(League source) {
        return LeagueRes.builder()
                        .id(source.getId())
                        .name(source.getName())
                        .iconUrls(iconUrlResConverter.convert(source.getIconUrls()))
                        .build();
    }

}