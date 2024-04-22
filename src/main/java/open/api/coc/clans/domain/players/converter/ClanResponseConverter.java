package open.api.coc.clans.domain.players.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.converter.IconUrlResConverter;
import open.api.coc.clans.domain.players.ClanResponse;
import open.api.coc.external.coc.clan.domain.common.Clan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClanResponseConverter implements Converter<Clan, ClanResponse> {

    private final IconUrlResConverter iconUrlResConverter;

    @Override
    public ClanResponse convert(Clan source) {
        return ClanResponse.builder()
                           .name(source.getName())
                           .tag(source.getTag())
                           .clanLevel(source.getClanLevel())
                           .badgeUrls(iconUrlResConverter.convert(source.getBadgeUrls()))
                           .build();
    }
}
