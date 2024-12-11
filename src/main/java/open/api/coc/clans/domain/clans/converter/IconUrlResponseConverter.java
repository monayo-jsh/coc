package open.api.coc.clans.domain.clans.converter;

import open.api.coc.clans.clean.infrastructure.common.persistence.entity.IconUrlEntity;
import open.api.coc.clans.domain.clans.IconUrlResponse;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IconUrlResponseConverter implements Converter<IconUrl, IconUrlResponse> {

    @Override
    public IconUrlResponse convert(IconUrl source) {
        return IconUrlResponse.builder()
                              .tiny(source.getTiny())
                              .small(source.getSmall())
                              .medium(source.getMedium())
                              .large(source.getLarge())
                              .build();
    }

    public IconUrlResponse convert(IconUrlEntity source) {
        return IconUrlResponse.builder()
                              .tiny(source.getTiny())
                              .small(source.getSmall())
                              .medium(source.getMedium())
                              .large(source.getLarge())
                              .build();
    }

}