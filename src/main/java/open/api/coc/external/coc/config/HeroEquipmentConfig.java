package open.api.coc.external.coc.config;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroEquipmentConfig {

    // 바바리안 킹
    BARBARIAN_PUPPET(100, "Barbarian Puppet", "normal"),
    EARTHQUAKE_BOOTS(101, "Earthquake Boots", "normal"),
    RAGE_VIAL(102, "Rage Vial", "normal"),
    VAMPSTACHE(103, "Vampstache", "normal"),

    GIANT_GAUNTLET(120, "Giant Gauntlet", "epic"),
    SPIKY_BALL(121, "Spiky Ball", "epic"),

    // 아처 퀸
    ARCHER_PUPPET(200, "Archer Puppet", "normal"),
    GIANT_ARROW(201, "Giant Arrow", "normal"),
    INVISIBILITY_VIAL(202, "Invisibility Vial", "normal"),
    HEALER_PUPPET(203, "Healer Puppet", "normal"),

    FROZEN_ARROW(220, "Frozen Arrow", "epic"),

    // 그랜드 워든
    LIFE_GEM(300, "Life Gem", "normal"),
    RAGE_GEM(301, "Rage Gem", "normal"),
    ETERNAL_TOME(302, "Eternal Tome", "normal"),
    HEALING_TOME(303, "Healing Tome", "normal"),

    FIREBALL(320, "Fireball", "epic"),

    // 로얄 챔피언
    ROYAL_GEM(400, "Royal Gem", "normal"),
    HASTE_VIAL(401, "Haste Vial", "normal"),
    SEEKING_SHIELD(402, "Seeking Shield", "normal"),
    HOG_RIDER_PUPPET(403, "Hog Rider Puppet", "normal"),

    UNKNOWN(Integer.MAX_VALUE, "unknown", "unknown");

    private final int code;
    private final String name;
    private final String rarity;

    HeroEquipmentConfig(int code, String name, String rarity) {
        this.code = code;
        this.name = name;
        this.rarity = rarity;
    }

    public static HeroEquipmentConfig findByName(String name) {
        return Arrays.stream(values())
                     .filter(hero -> Objects.equals(hero.getName(), name))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("영웅 장비 코드를 찾지 못함"));
    }
}
