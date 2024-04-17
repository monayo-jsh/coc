package open.api.coc.clans.domain.common.converter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;
import open.api.coc.clans.domain.common.HeroResponse;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.config.HeroConfig;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class HeroResponseConverter implements Converter<Hero, HeroResponse> {

    private final HeroEquipmentResponseConverter heroEquipmentResponseConverter;
    @Override
    public HeroResponse convert(Hero source) {
        return HeroResponse.builder()
                           .code(getHeroCode(source.getName()))
                           .name(source.getName())
                           .village(source.getVillage())
                           .level(source.getLevel())
                           .maxLevel(source.getMaxLevel())
                           .equipments(makeEquipments(source.getEquipment()))
                           .build();
    }

    private List<HeroEquipmentResponse> makeEquipments(List<HeroEquipment> equipments) {
        if (CollectionUtils.isEmpty(equipments)) {
            return Collections.emptyList();
        }

        return equipments.stream()
                         .map(heroEquipmentResponseConverter::convert)
                         .collect(Collectors.toList());
    }

    private Integer getHeroCode(String name) {
        try {
            HeroConfig hero = HeroConfig.findByName(name);
            return hero.getCode();
        } catch (Exception ignored) {
            return -1;
        }
    }

}
