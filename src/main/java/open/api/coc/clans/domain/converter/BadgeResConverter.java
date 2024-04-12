package open.api.coc.clans.domain.converter;

import open.api.coc.clans.domain.BadgeRes;
import open.api.coc.external.coc.clan.domain.clan.BadgeUrl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BadgeResConverter implements Converter<BadgeUrl, BadgeRes> {

    @Override
    public BadgeRes convert(BadgeUrl source) {
        return BadgeRes.builder()
                       .small(source.getSmall())
                       .medium(source.getMedium())
                       .large(source.getLarge())
                       .build();
    }

}