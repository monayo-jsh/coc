package open.api.coc.external.coc.config;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroEquipmentConfig {

    GIANT_GAUNTLET(1, "Giant Gauntlet"),
    FROZEN_ARROW(2, "Frozen Arrow"),
    FIREBALL(3, "Fireball"),
    BARBARIAN_PUPPET(4, "Barbarian Puppet"),
    RAGE_VIAL(5, "Rage Vial"),
    ARCHER_PUPPET(6, "Archer Puppet"),
    INVISIBILITY_VIAL(7, "Invisibility Vial"),
    ETERNAL_TOME(8, "Eternal Tome"),
    LIFE_GEM(9, "Life Gem"),
    SEEKING_SHIELD(10, "Seeking Shield"),
    ROYAL_GEM(11, "Royal Gem"),
    EARTHQUAKE_BOOTS(12, "Earthquake Boots"),
    HOG_RIDER_PUPPET(13, "Hog Rider Puppet"),
    VAMPSTACHE(14, "Vampstache"),
    HASTE_VIAL(15, "Haste Vial"),
    GIANT_ARROW(16, "Giant Arrow"),
    HEALER_PUPPET(17, "Healer Puppet"),
    RAGE_GEM(18, "Rage Gem"),
    HEALING_TOME(19, "Healing Tome");

    private final int code;
    private final String name;

    HeroEquipmentConfig(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static HeroEquipmentConfig findByName(String name) {
        return Arrays.stream(values())
                     .filter(hero -> Objects.equals(hero.getName(), name))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("영웅 장비 코드를 찾지 못함"));
    }
}
