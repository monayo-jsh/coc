package open.api.coc.external.coc.config;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroEquipmentConfig {

    // 바바리안 킹
    BARBARIAN_PUPPET(100, "Barbarian Puppet"),
    EARTHQUAKE_BOOTS(101, "Earthquake Boots"),
    GIANT_GAUNTLET(102, "Giant Gauntlet"),
    RAGE_VIAL(103, "Rage Vial"),
    VAMPSTACHE(104, "Vampstache"),

    // 아처 퀸
    ARCHER_PUPPET(200, "Archer Puppet"),
    GIANT_ARROW(201, "Giant Arrow"),
    FROZEN_ARROW(202, "Frozen Arrow"),
    INVISIBILITY_VIAL(203, "Invisibility Vial"),
    HEALER_PUPPET(204, "Healer Puppet"),

    // 그랜드 워든
    LIFE_GEM(300, "Life Gem"),
    RAGE_GEM(301, "Rage Gem"),
    FIREBALL(302, "Fireball"),
    ETERNAL_TOME(303, "Eternal Tome"),
    HEALING_TOME(304, "Healing Tome"),

    // 로얄 챔피언
    ROYAL_GEM(400, "Royal Gem"),
    HASTE_VIAL(401, "Haste Vial"),
    SEEKING_SHIELD(402, "Seeking Shield"),
    HOG_RIDER_PUPPET(403, "Hog Rider Puppet");

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
