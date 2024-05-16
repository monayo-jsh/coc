package open.api.coc.clans.domain.common.converter;

import open.api.coc.clans.domain.common.HeroEquipmentResponse;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.config.HeroEquipmentConfig;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class HeroEquipmentResponseConverter implements Converter<HeroEquipment, HeroEquipmentResponse> {

    @Override
    public HeroEquipmentResponse convert(HeroEquipment source) {
        HeroEquipmentConfig heroEquipment = HeroEquipmentConfig.findByName(source.getName());
        return HeroEquipmentResponse.builder()
                                    .code(heroEquipment.getCode())
                                    .name(source.getName())
                                    .village(source.getVillage())
                                    .level(source.getLevel())
                                    .maxLevel(source.getMaxLevel())
                                    .rarity(heroEquipment.getRarity())
                                    .build();
    }
}
