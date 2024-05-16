package open.api.coc.clans.database.entity.common.converter;

import open.api.coc.clans.database.entity.common.IconUrlEntity;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IconUrlEntityConverter implements Converter<IconUrl, IconUrlEntity> {

    @Override
    public IconUrlEntity convert(IconUrl source) {
        return IconUrlEntity.builder()
                            .tiny(source.getTiny())
                            .small(source.getSmall())
                            .medium(source.getMedium())
                            .large(source.getLarge())
                            .build();
    }

}
