package open.api.coc.clans.database.entity.player.common;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Troop {

    BARBARIAN(TroopType.ELIXIR, "Barbarian", "바바리안"),
    ARCHER(TroopType.ELIXIR, "Archer", "아처"),
    GIANT(TroopType.ELIXIR, "Giant", "자이언트"),
    GOBLIN(TroopType.ELIXIR, "Goblin", "고블린"),
    WALL_BREAKER(TroopType.ELIXIR, "Wall Breaker", "해골 돌격병"),
    BALLOON(TroopType.ELIXIR, "Balloon", "해골 비행선"),
    WIZARD(TroopType.ELIXIR, "Wizard", "마법사"),
    DRAGON(TroopType.ELIXIR, "Dragon", "드래곤"),
    HEALER(TroopType.ELIXIR, "Healer", "치유사"),
    PEKKA(TroopType.ELIXIR, "P.E.K.K.A", "페카"),
    BABY_DRAGON(TroopType.ELIXIR, "Baby Dragon", "베이비 드래곤"),
    MINER(TroopType.ELIXIR, "Miner", "광부"),
    ELECTRO_DRAGON(TroopType.ELIXIR, "Electro Dragon", "일렉트로 드래곤"),
    YETI(TroopType.ELIXIR, "Yeti", "예티"),
    DRAGON_RIDER(TroopType.ELIXIR, "Dragon Rider", "드래곤 라이더"),
    ELECTRO_TITAN(TroopType.ELIXIR, "Electro Titan", "일렉트로 타이탄"),
    ROOT_RIDER(TroopType.ELIXIR, "Root Rider", "트리 라이더"),

    MINION(TroopType.DARK_ELIXIR, "Minion", "미니언"),
    HOG_RIDER(TroopType.DARK_ELIXIR, "Hog Rider", "호그 라이더"),
    VALKYRIE(TroopType.DARK_ELIXIR, "Valkyrie", "발키리"),
    GOLEM(TroopType.DARK_ELIXIR, "Golem", "골렘"),
    WITCH(TroopType.DARK_ELIXIR, "Witch", "마녀"),
    LAVA_HOUND(TroopType.DARK_ELIXIR, "Lava Hound", "라바 하운드"),
    BOWLER(TroopType.DARK_ELIXIR, "Bowler", "볼러"),
    ICE_GOLEM(TroopType.DARK_ELIXIR, "Ice Golem", "아이스 골렘"),
    HEADHUNTER(TroopType.DARK_ELIXIR, "Headhunter", "헤드헌터"),
    APPRENTICE_WARDEN(TroopType.DARK_ELIXIR, "Apprentice Warden", "견습생 워든"),

    SUPER_BARBARIAN(TroopType.SUPER_TROOP, "Super Barbarian", "슈퍼 바바리안"),
    SUPER_ARCHER(TroopType.SUPER_TROOP, "Super Archer", "슈퍼 아처"),
    SNEAKY_GOBLIN(TroopType.SUPER_TROOP, "Sneaky Goblin", "도둑 고블린"),
    SUPER_WALL_BREAKER(TroopType.SUPER_TROOP, "Super Wall Breaker", "슈퍼 해골 돌격병"),
    SUPER_GIANT(TroopType.SUPER_TROOP, "Super Giant", "슈퍼 자이언트"),
    ROCKET_BALLOON(TroopType.SUPER_TROOP, "Rocket Balloon", "로켓 비행선"),
    SUPER_WIZARD(TroopType.SUPER_TROOP, "Super Wizard", "슈퍼 마법사"),
    SUPER_DRAGON(TroopType.SUPER_TROOP, "Super Dragon", "슈퍼 드래곤"),
    INFERNO_DRAGON(TroopType.SUPER_TROOP, "Inferno Dragon", "인페르노 드래곤"),
    SUPER_MINION(TroopType.SUPER_TROOP, "Super Minion", "슈퍼 미니언"),
    SUPER_VALKYRIE(TroopType.SUPER_TROOP, "Super Valkyrie", "슈퍼 발키리"),
    SUPER_WITCH(TroopType.SUPER_TROOP, "Super Witch", "슈퍼 마녀"),
    ICE_HOUND(TroopType.SUPER_TROOP, "Ice Hound", "아이스 하운드"),
    SUPER_BOWLER(TroopType.SUPER_TROOP, "Super Bowler", "슈퍼 볼러"),
    SUPER_MINER(TroopType.SUPER_TROOP, "Super Miner", "능력자 광부"),
    SUPER_HOG_RIDER(TroopType.SUPER_TROOP, "Super Hog Rider", "슈퍼 호그 라이더"),

    WALL_WRECKER(TroopType.SIEGE_MACHINE, "Wall Wrecker", "파괴 전차"),
    BATTLE_BLIMP(TroopType.SIEGE_MACHINE, "Battle Blimp", "전투 비행선"),
    STONE_SLAMMER(TroopType.SIEGE_MACHINE, "Stone Slammer", "바위 비행선"),
    SIEGE_BARRACKS(TroopType.SIEGE_MACHINE, "Siege Barracks", "시즈 훈련소"),
    LOG_LAUNCHER(TroopType.SIEGE_MACHINE, "Log Launcher", "통나무 발사기"),
    FLAME_FLINGER(TroopType.SIEGE_MACHINE, "Flame Flinger", "화염 투척기"),
    BATTLE_DRILL(TroopType.SIEGE_MACHINE, "Battle Drill", "배틀 드릴"),

    LASSI(TroopType.PET, "L.A.S.S.I",  "라씨"),
    MIGHTY_YAK(TroopType.PET, "Mighty Yak", "마이트 야크"),
    ELECTRO_OWL(TroopType.PET, "Electro Owl", "일렉트로 아울"),
    UNICORN(TroopType.PET, "Unicorn",  "유니콘"),
    PHOENIX(TroopType.PET, "Phoenix",  "피닉스"),
    POISON_LIZARD(TroopType.PET, "Poison Lizard",  "독마뱀"),
    DIGGY(TroopType.PET, "Diggy",  "디기"),
    FROSTY(TroopType.PET, "Frosty",  "프로스티"),
    SPIRIT_FOX(TroopType.PET, "Spirit Fox", "스피릿 폭스"),
    ANGRY_JELLY(TroopType.PET, "Angry Jelly", "앵그리 젤리"),

    UNKNOWN(TroopType.UNKNOWN, "unknown", "알 수 없음");

    private final TroopType type;
    private final String name;
    private final String koreanName;

    Troop(TroopType type, String name, String koreanName) {
        this.type = type;
        this.name = name;
        this.koreanName = koreanName;
    }

    public static Troop findByName(String troopName) {
        return Arrays.stream(values())
                     .filter(troop -> Objects.equals(troop.getName(), troopName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

    public static boolean isPet(String troopName) {
        return Objects.equals(findByName(troopName).getType(), TroopType.PET);
    }

    public static boolean isSiegeMachine(String troopName) {
        return Objects.equals(findByName(troopName).getType(), TroopType.SIEGE_MACHINE);
    }
}
