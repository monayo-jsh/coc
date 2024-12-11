package open.api.coc.clans.domain.common.converter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerHeroEntity;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;
import open.api.coc.clans.domain.common.HeroResponse;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.config.HeroConfig;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class HeroResponseConverter implements Converter<Hero, HeroResponse> {

    private final HeroEquipmentResponseConverter heroEquipmentResponseConverter;
    @Override
    public HeroResponse convert(Hero source) {
        HeroConfig hero = getHeroConfig(source.getName());
        return HeroResponse.builder()
                           .code(hero.getCode())
                           .name(source.getName())
                           .koreanName(hero.getKoreanName())
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

    private HeroConfig getHeroConfig(String name) {
        try {
            return HeroConfig.findByName(name);
        } catch (Exception ignored) {
            return HeroConfig.UNKNOWN;
        }
    }

    public @NonNull HeroResponse convert(PlayerHeroEntity source) {
        HeroConfig hero = getHeroConfig(source.getId().getName());
        return HeroResponse.builder()
                           .code(hero.getCode())
                           .name(source.getId().getName())
                           .koreanName(hero.getKoreanName())
                           .village("home")
                           .level(source.getLevelInfo().getLevel())
                           .maxLevel(source.getLevelInfo().getMaxLevel())
                           .equipments(new ArrayList<>())
                           .build();
    }

}
