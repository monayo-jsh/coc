package open.api.coc.clans.database.entity.player.common;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Spell {

    LIGHTNING_SPELL(SpellType.ELIXIR, 1, "Lightning Spell", "번개 마법"),
    HEALING_SPELL(SpellType.ELIXIR, 2, "Healing Spell", "치유 마법"),
    RAGE_SPELL(SpellType.ELIXIR, 3, "Rage Spell", "분노 마법"),
    JUMP_SPELL(SpellType.ELIXIR, 4, "Jump Spell", "이동 마법"),
    FREEZE_SPELL(SpellType.ELIXIR, 5, "Freeze Spell", "얼음 마법"),
    CLONE_SPELL(SpellType.ELIXIR, 6, "Clone Spell", "복제 마법"),
    INVISIBILITY_SPELL(SpellType.ELIXIR, 7, "Invisibility Spell", "투명 마법"),
    RECALL_SPELL(SpellType.ELIXIR, 8, "Recall Spell", "복귀 마법"),

    POISON_SPELL(SpellType.DARK_ELIXIR, 1, "Poison Spell", "독 마법"),
    EARTHQUAKE_SPELL(SpellType.DARK_ELIXIR, 2, "Earthquake Spell", "지진 마법"),
    HASTE_SPELL(SpellType.DARK_ELIXIR, 3, "Haste Spell", "신속 마법"),
    SKELETON_SPELL(SpellType.DARK_ELIXIR, 4, "Skeleton Spell", "해골 마법"),
    BAT_SPELL(SpellType.DARK_ELIXIR, 5, "Bat Spell", "박쥐 마법"),
    OVERGROWTH_SPELL(SpellType.DARK_ELIXIR, 6, "Overgrowth Spell", "과성장 마법"),

    UNKNOWN(SpellType.UNKNOWN, 1, "unknown", "알 수 없음");

    private final SpellType type;
    private final Integer order;
    private final String name;
    private final String koreanName;

    Spell(SpellType type, Integer order, String name, String koreanName) {
        this.type = type;
        this.order = order;
        this.name = name;
        this.koreanName = koreanName;
    }

    public static Spell findByName(String troopName) {
        return Arrays.stream(values())
                     .filter(troop -> Objects.equals(troop.getName(), troopName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

}
