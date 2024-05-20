package open.api.coc.clans.domain.players.converter;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.clans.converter.IconUrlResponseConverter;
import open.api.coc.clans.domain.players.PlayerClanResponse;
import open.api.coc.external.coc.clan.domain.common.PlayerClan;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayerClanResponseConverter implements Converter<PlayerClan, PlayerClanResponse> {

    private final IconUrlResponseConverter iconUrlResConverter;

    @Override
    public PlayerClanResponse convert(PlayerClan source) {
        return PlayerClanResponse.builder()
                                 .name(source.getName())
                                 .tag(source.getTag())
                                 .clanLevel(source.getClanLevel())
                                 .badgeUrls(iconUrlResConverter.convert(source.getBadgeUrls()))
                                 .build();
    }
}
