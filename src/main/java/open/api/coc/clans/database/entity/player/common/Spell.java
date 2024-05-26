package open.api.coc.clans.database.entity.player.common;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Spell {

    LIGHTNING_SPELL(SpellType.ELIXIR, "Lightning Spell", "번개 마법"),
    HEALING_SPELL(SpellType.ELIXIR, "Healing Spell", "치유 마법"),
    RAGE_SPELL(SpellType.ELIXIR, "Rage Spell", "분노 마법"),
    JUMP_SPELL(SpellType.ELIXIR, "Jump Spell", "이동 마법"),
    FREEZE_SPELL(SpellType.ELIXIR, "Freeze Spell", "얼음 마법"),
    CLONE_SPELL(SpellType.ELIXIR, "Clone Spell", "복제 마법"),
    INVISIBILITY_SPELL(SpellType.ELIXIR, "Invisibility Spell", "투명 마법"),
    RECALL_SPELL(SpellType.ELIXIR, "Recall Spell", "복귀 마법"),

    POISON_SPELL(SpellType.DARK_ELIXIR, "Poison Spell", "독 마법"),
    EARTHQUAKE_SPELL(SpellType.DARK_ELIXIR, "Earthquake Spell", "지진 마법"),
    HASTE_SPELL(SpellType.DARK_ELIXIR, "Haste Spell", "신속 마법"),
    SKELETON_SPELL(SpellType.DARK_ELIXIR, "Skeleton Spell", "해골 마법"),
    BAT_SPELL(SpellType.DARK_ELIXIR, "Bat Spell", "박쥐 마법"),
    OVERGROWTH_SPELL(SpellType.DARK_ELIXIR, "Overgrowth Spell", "과성장 마법"),

    UNKNOWN(SpellType.UNKNOWN, "unknown", "알 수 없음");

    private final SpellType type;
    private final String name;
    private final String koreanName;

    Spell(SpellType type, String name, String koreanName) {
        this.type = type;
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
