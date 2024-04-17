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
        return HeroEquipmentResponse.builder()
                                    .code(getHeroEquipmentCode(source.getName()))
                                    .name(source.getName())
                                    .village(source.getVillage())
                                    .level(source.getLevel())
                                    .maxLevel(source.getMaxLevel())
                                    .build();
    }

    private Integer getHeroEquipmentCode(String name) {
        try {
            HeroEquipmentConfig heroEquipment = HeroEquipmentConfig.findByName(name);
            return heroEquipment.getCode();
        } catch (Exception ignored) {
            return -1;
        }
    }
}
