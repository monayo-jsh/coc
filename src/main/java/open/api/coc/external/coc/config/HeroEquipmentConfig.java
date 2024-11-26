package open.api.coc.external.coc.config;

import static open.api.coc.external.coc.config.HeroConfig.ARCHER_QUEEN;
import static open.api.coc.external.coc.config.HeroConfig.BARBARIAN_KING;
import static open.api.coc.external.coc.config.HeroConfig.GRAND_WARDEN;
import static open.api.coc.external.coc.config.HeroConfig.MINION_PRINCE;
import static open.api.coc.external.coc.config.HeroConfig.ROYAL_CHAMPION;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum HeroEquipmentConfig {

    // 바바리안 킹
    BARBARIAN_PUPPET(BARBARIAN_KING, 100, "normal", "Barbarian Puppet", "바바리안 인형"),
    EARTHQUAKE_BOOTS(BARBARIAN_KING, 101, "normal", "Earthquake Boots", "지진 부츠"),
    RAGE_VIAL(BARBARIAN_KING, 102, "normal", "Rage Vial", "분노 마법 병"),
    VAMPSTACHE(BARBARIAN_KING, 103, "normal", "Vampstache", "흡혈 수염"),

    GIANT_GAUNTLET(BARBARIAN_KING, 120, "epic", "Giant Gauntlet", "자이언트 건틀릿"),
    SPIKY_BALL(BARBARIAN_KING, 121, "epic", "Spiky Ball", "스파이키 볼"),

    // 아처 퀸
    ARCHER_PUPPET(ARCHER_QUEEN, 200, "normal", "Archer Puppet", "아처 인형"),
    GIANT_ARROW(ARCHER_QUEEN, 201, "normal", "Giant Arrow", "자이언트 화살"),
    INVISIBILITY_VIAL(ARCHER_QUEEN, 202, "normal", "Invisibility Vial", "투명 마법 병"),
    HEALER_PUPPET(ARCHER_QUEEN, 203, "normal", "Healer Puppet", "치유사 인형"),

    FROZEN_ARROW(ARCHER_QUEEN, 220, "epic", "Frozen Arrow", "얼음 화살"),
    MAGIC_MIRROR(ARCHER_QUEEN, 221, "epic", "Magic Mirror", "마법 반사경"),

    // 그랜드 워든
    LIFE_GEM(GRAND_WARDEN, 300, "normal", "Life Gem", "생명의 보석"),
    RAGE_GEM(GRAND_WARDEN, 301, "normal", "Rage Gem", "분노 보석"),
    ETERNAL_TOME(GRAND_WARDEN, 302, "normal", "Eternal Tome", "영원의 책"),
    HEALING_TOME(GRAND_WARDEN, 303, "normal", "Healing Tome", "치유의 책"),

    LAVALOON_PUPPET(GRAND_WARDEN, 320, "epic", "Lavaloon Puppet", "라벌 인형"),

    FIREBALL(GRAND_WARDEN, 320, "epic", "Fireball", "파이어 볼"),

    // 로얄 챔피언
    ROYAL_GEM(ROYAL_CHAMPION, 400, "normal", "Royal Gem", "로얄 보석"),
    HASTE_VIAL(ROYAL_CHAMPION, 401, "normal", "Haste Vial", "신속 마법 병"),
    SEEKING_SHIELD(ROYAL_CHAMPION, 402, "normal", "Seeking Shield", "추적 방패"),
    HOG_RIDER_PUPPET(ROYAL_CHAMPION, 403, "normal", "Hog Rider Puppet", "호그 라이더 인형"),

    ROCKET_SPEAR(ROYAL_CHAMPION, 420, "epic", "Rocket Spear", "로켓창"),

    // 미니언 프린스
    HENCHMEN_PUPPET(MINION_PRINCE, 500, "normal", "Henchmen Puppet", "보디가드 인형"),
    DARK_ORB(MINION_PRINCE, 501, "normal", "Dark Orb", "다크 구슬"),

    UNKNOWN(HeroConfig.UNKNOWN, Integer.MAX_VALUE, "unknown", "unknown", "unknown");

    private final HeroConfig hero;
    private final int code;
    private final String rarity;
    private final String name;
    private final String koreanName;

    HeroEquipmentConfig(HeroConfig hero, int code, String rarity, String name, String koreanName) {
        this.hero = hero;
        this.code = code;
        this.rarity = rarity;
        this.name = name;
        this.koreanName = koreanName;
    }

    public static HeroEquipmentConfig findByName(String heroEquipmentName) {
        return Arrays.stream(values())
                     .filter(heroEquipmentConfig -> Objects.equals(heroEquipmentConfig.getName(), heroEquipmentName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

    public boolean isUnknown() {
        return Objects.equals(UNKNOWN, this);
    }
}
