package open.api.coc.external.coc.config;

import static open.api.coc.external.coc.config.HeroConfig.ARCHER_QUEEN;
import static open.api.coc.external.coc.config.HeroConfig.BARBARIAN_KING;
import static open.api.coc.external.coc.config.HeroConfig.GRAND_WARDEN;
import static open.api.coc.external.coc.config.HeroConfig.ROYAL_CHAMPION;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroEquipmentConfig {

    // 바바리안 킹
    BARBARIAN_PUPPET(BARBARIAN_KING, 100, "Barbarian Puppet", "normal"),
    EARTHQUAKE_BOOTS(BARBARIAN_KING, 101, "Earthquake Boots", "normal"),
    RAGE_VIAL(BARBARIAN_KING, 102, "Rage Vial", "normal"),
    VAMPSTACHE(BARBARIAN_KING, 103, "Vampstache", "normal"),

    GIANT_GAUNTLET(BARBARIAN_KING, 120, "Giant Gauntlet", "epic"),
    SPIKY_BALL(BARBARIAN_KING, 121, "Spiky Ball", "epic"),

    // 아처 퀸
    ARCHER_PUPPET(ARCHER_QUEEN, 200, "Archer Puppet", "normal"),
    GIANT_ARROW(ARCHER_QUEEN, 201, "Giant Arrow", "normal"),
    INVISIBILITY_VIAL(ARCHER_QUEEN, 202, "Invisibility Vial", "normal"),
    HEALER_PUPPET(ARCHER_QUEEN, 203, "Healer Puppet", "normal"),

    FROZEN_ARROW(ARCHER_QUEEN, 220, "Frozen Arrow", "epic"),

    // 그랜드 워든
    LIFE_GEM(GRAND_WARDEN, 300, "Life Gem", "normal"),
    RAGE_GEM(GRAND_WARDEN, 301, "Rage Gem", "normal"),
    ETERNAL_TOME(GRAND_WARDEN, 302, "Eternal Tome", "normal"),
    HEALING_TOME(GRAND_WARDEN, 303, "Healing Tome", "normal"),

    FIREBALL(GRAND_WARDEN, 320, "Fireball", "epic"),

    // 로얄 챔피언
    ROYAL_GEM(ROYAL_CHAMPION, 400, "Royal Gem", "normal"),
    HASTE_VIAL(ROYAL_CHAMPION, 401, "Haste Vial", "normal"),
    SEEKING_SHIELD(ROYAL_CHAMPION, 402, "Seeking Shield", "normal"),
    HOG_RIDER_PUPPET(ROYAL_CHAMPION, 403, "Hog Rider Puppet", "normal"),

    UNKNOWN(HeroConfig.UNKNOWN, Integer.MAX_VALUE, "unknown", "unknown");

    private final HeroConfig hero;
    private final int code;
    private final String name;
    private final String rarity;

    HeroEquipmentConfig(HeroConfig hero, int code, String name, String rarity) {
        this.hero = hero;
        this.code = code;
        this.name = name;
        this.rarity = rarity;
    }

    public static HeroEquipmentConfig findByName(String heroEquipmentName) {
        return Arrays.stream(values())
                     .filter(heroEquipmentConfig -> Objects.equals(heroEquipmentConfig.getName(), heroEquipmentName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

}
