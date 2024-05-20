package open.api.coc.clans.database.entity.player.common;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Troop {

    BARBARIAN(TroopType.ELIXIR, "Barbarian"),
    ARCHER(TroopType.ELIXIR, "Archer"),
    GIANT(TroopType.ELIXIR, "Giant"),
    GOBLIN(TroopType.ELIXIR, "Goblin"),
    WALL_BREAKER(TroopType.ELIXIR, "Wall Breaker"),
    BALLOON(TroopType.ELIXIR, "Balloon"),
    WIZARD(TroopType.ELIXIR, "Wizard"),
    HEALER(TroopType.ELIXIR, "Healer"),
    DRAGON(TroopType.ELIXIR, "Dragon"),
    PEKKA(TroopType.ELIXIR, "P.E.K.K.A"),
    BABY_DRAGON(TroopType.ELIXIR, "Baby Dragon"),
    MINER(TroopType.ELIXIR, "Miner"),
    YETI(TroopType.ELIXIR, "Yeti"),
    ELECTRO_DRAGON(TroopType.ELIXIR, "Electro Dragon"),
    DRAGON_RIDER(TroopType.ELIXIR, "Dragon Rider"),
    ELECTRO_TITAN(TroopType.ELIXIR, "Electro Titan"),
    APPRENTICE_WARDEN(TroopType.ELIXIR, "Apprentice Warden"),
    ROOT_RIDER(TroopType.ELIXIR, "Root Rider"),

    MINION(TroopType.DARK_ELIXIR, "Minion"),
    HOG_RIDER(TroopType.DARK_ELIXIR, "Hog Rider"),
    VALKYRIE(TroopType.DARK_ELIXIR, "Valkyrie"),
    GOLEM(TroopType.DARK_ELIXIR, "Golem"),
    WITCH(TroopType.DARK_ELIXIR, "Witch"),
    LAVA_HOUND(TroopType.DARK_ELIXIR, "Lava Hound"),
    BOWLER(TroopType.DARK_ELIXIR, "Bowler"),
    ICE_GOLEM(TroopType.DARK_ELIXIR, "Ice Golem"),
    HEADHUNTER(TroopType.DARK_ELIXIR, "Headhunter"),

    SUPER_BARBARIAN(TroopType.SUPER_TROOP, "Super Barbarian"),
    SUPER_ARCHER(TroopType.SUPER_TROOP, "Super Archer"),
    SUPER_WALL_BREAKER(TroopType.SUPER_TROOP, "Super Wall Breaker"),
    SUPER_GIANT(TroopType.SUPER_TROOP, "Super Giant"),
    SNEAKY_GOBLIN(TroopType.SUPER_TROOP, "Sneaky Goblin"),
    SUPER_MINER(TroopType.SUPER_TROOP, "Super Miner"),
    ROCKET_BALLOON(TroopType.SUPER_TROOP, "Rocket Balloon"),
    INFERNO_DRAGON(TroopType.SUPER_TROOP, "Inferno Dragon"),
    SUPER_VALKYRIE(TroopType.SUPER_TROOP, "Super Valkyrie"),
    SUPER_WITCH(TroopType.SUPER_TROOP, "Super Witch"),
    ICE_HOUND(TroopType.SUPER_TROOP, "Ice Hound"),
    SUPER_BOWLER(TroopType.SUPER_TROOP, "Super Bowler"),
    SUPER_DRAGON(TroopType.SUPER_TROOP, "Super Dragon"),
    SUPER_WIZARD(TroopType.SUPER_TROOP, "Super Wizard"),
    SUPER_MINION(TroopType.SUPER_TROOP, "Super Minion"),
    SUPER_HOG_RIDER(TroopType.SUPER_TROOP, "Super Hog Rider"),

    WALL_WRECKER(TroopType.SIEGE_MACHINE, "Wall Wrecker"),
    BATTLE_BLIMP(TroopType.SIEGE_MACHINE, "Battle Blimp"),
    STONE_SLAMMER(TroopType.SIEGE_MACHINE, "Stone Slammer"),
    SIEGE_BARRACKS(TroopType.SIEGE_MACHINE, "Siege Barracks"),
    LOG_LAUNCHER(TroopType.SIEGE_MACHINE, "Log Launcher"),
    FLAME_FLINGER(TroopType.SIEGE_MACHINE, "Flame Flinger"),
    BATTLE_DRILL(TroopType.SIEGE_MACHINE, "Battle Drill"),

    LASSI(TroopType.PET, "L.A.S.S.I"),
    MIGHTY_YAK(TroopType.PET, "Mighty Yak"),
    ELECTRO_OWL(TroopType.PET, "Electro Owl"),
    UNICORN(TroopType.PET, "Unicorn"),
    PHOENIX(TroopType.PET, "Phoenix"),
    POISON_LIZARD(TroopType.PET, "Poison Lizard"),
    DIGGY(TroopType.PET, "Diggy"),
    FROSTY(TroopType.PET, "Frosty"),
    SPIRIT_FOX(TroopType.PET, "Spirit Fox"),
    ANGRY_JELLY(TroopType.PET, "Angry Jelly"),

    UNKNOWN(TroopType.UNKNOWN, "unknown");

    private final TroopType type;
    private final String name;

    Troop(TroopType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Troop findByName(String troopName) {
        return Arrays.stream(values())
                     .filter(troop -> Objects.equals(troop.getName(), troopName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

}
