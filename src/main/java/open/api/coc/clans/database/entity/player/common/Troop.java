package open.api.coc.clans.database.entity.player.common;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Troop {

    BARBARIAN(TroopType.ELIXIR, 1, "Barbarian", "바바리안"),
    ARCHER(TroopType.ELIXIR, 2, "Archer", "아처"),
    GIANT(TroopType.ELIXIR, 3, "Giant", "자이언트"),
    GOBLIN(TroopType.ELIXIR, 4, "Goblin", "고블린"),
    WALL_BREAKER(TroopType.ELIXIR, 5, "Wall Breaker", "해골 돌격병"),
    BALLOON(TroopType.ELIXIR, 6, "Balloon", "해골 비행선"),
    WIZARD(TroopType.ELIXIR, 7, "Wizard", "마법사"),
    DRAGON(TroopType.ELIXIR, 8, "Dragon", "드래곤"),
    HEALER(TroopType.ELIXIR, 9, "Healer", "치유사"),
    PEKKA(TroopType.ELIXIR, 10, "P.E.K.K.A", "페카"),
    BABY_DRAGON(TroopType.ELIXIR, 11, "Baby Dragon", "베이비 드래곤"),
    MINER(TroopType.ELIXIR, 12, "Miner", "광부"),
    ELECTRO_DRAGON(TroopType.ELIXIR, 13, "Electro Dragon", "일렉트로 드래곤"),
    YETI(TroopType.ELIXIR, 14, "Yeti", "예티"),
    DRAGON_RIDER(TroopType.ELIXIR, 15, "Dragon Rider", "드래곤 라이더"),
    ELECTRO_TITAN(TroopType.ELIXIR, 16, "Electro Titan", "일렉트로 타이탄"),
    ROOT_RIDER(TroopType.ELIXIR, 17, "Root Rider", "트리 라이더"),

    MINION(TroopType.DARK_ELIXIR, 1, "Minion", "미니언"),
    HOG_RIDER(TroopType.DARK_ELIXIR, 2, "Hog Rider", "호그 라이더"),
    VALKYRIE(TroopType.DARK_ELIXIR, 3, "Valkyrie", "발키리"),
    GOLEM(TroopType.DARK_ELIXIR, 4, "Golem", "골렘"),
    WITCH(TroopType.DARK_ELIXIR, 5, "Witch", "마녀"),
    LAVA_HOUND(TroopType.DARK_ELIXIR, 6, "Lava Hound", "라바 하운드"),
    BOWLER(TroopType.DARK_ELIXIR, 7, "Bowler", "볼러"),
    ICE_GOLEM(TroopType.DARK_ELIXIR, 8, "Ice Golem", "아이스 골렘"),
    HEADHUNTER(TroopType.DARK_ELIXIR, 9, "Headhunter", "헤드헌터"),
    APPRENTICE_WARDEN(TroopType.DARK_ELIXIR, 10, "Apprentice Warden", "견습생 워든"),

    SUPER_BARBARIAN(TroopType.SUPER_TROOP, 1, "Super Barbarian", "슈퍼 바바리안"),
    SUPER_ARCHER(TroopType.SUPER_TROOP, 2, "Super Archer", "슈퍼 아처"),
    SNEAKY_GOBLIN(TroopType.SUPER_TROOP, 3, "Sneaky Goblin", "도둑 고블린"),
    SUPER_WALL_BREAKER(TroopType.SUPER_TROOP, 4, "Super Wall Breaker", "슈퍼 해골 돌격병"),
    SUPER_GIANT(TroopType.SUPER_TROOP, 5, "Super Giant", "슈퍼 자이언트"),
    ROCKET_BALLOON(TroopType.SUPER_TROOP, 6, "Rocket Balloon", "로켓 비행선"),
    SUPER_WIZARD(TroopType.SUPER_TROOP, 7, "Super Wizard", "슈퍼 마법사"),
    SUPER_DRAGON(TroopType.SUPER_TROOP, 8, "Super Dragon", "슈퍼 드래곤"),
    INFERNO_DRAGON(TroopType.SUPER_TROOP, 9, "Inferno Dragon", "인페르노 드래곤"),
    SUPER_MINION(TroopType.SUPER_TROOP, 10, "Super Minion", "슈퍼 미니언"),
    SUPER_VALKYRIE(TroopType.SUPER_TROOP, 11, "Super Valkyrie", "슈퍼 발키리"),
    SUPER_WITCH(TroopType.SUPER_TROOP, 12, "Super Witch", "슈퍼 마녀"),
    ICE_HOUND(TroopType.SUPER_TROOP, 13, "Ice Hound", "아이스 하운드"),
    SUPER_BOWLER(TroopType.SUPER_TROOP, 14, "Super Bowler", "슈퍼 볼러"),
    SUPER_MINER(TroopType.SUPER_TROOP, 15, "Super Miner", "능력자 광부"),
    SUPER_HOG_RIDER(TroopType.SUPER_TROOP, 16, "Super Hog Rider", "슈퍼 호그 라이더"),

    WALL_WRECKER(TroopType.SIEGE_MACHINE, 1, "Wall Wrecker", "파괴 전차"),
    BATTLE_BLIMP(TroopType.SIEGE_MACHINE, 2, "Battle Blimp", "전투 비행선"),
    STONE_SLAMMER(TroopType.SIEGE_MACHINE, 3, "Stone Slammer", "바위 비행선"),
    SIEGE_BARRACKS(TroopType.SIEGE_MACHINE, 4, "Siege Barracks", "시즈 훈련소"),
    LOG_LAUNCHER(TroopType.SIEGE_MACHINE, 5, "Log Launcher", "통나무 발사기"),
    FLAME_FLINGER(TroopType.SIEGE_MACHINE, 6, "Flame Flinger", "화염 투척기"),
    BATTLE_DRILL(TroopType.SIEGE_MACHINE, 7, "Battle Drill", "배틀 드릴"),

    LASSI(TroopType.PET, 1, "L.A.S.S.I",  "라씨"),
    MIGHTY_YAK(TroopType.PET, 2, "Mighty Yak", "마이트 야크"),
    ELECTRO_OWL(TroopType.PET, 3, "Electro Owl", "일렉트로 아울"),
    UNICORN(TroopType.PET, 4, "Unicorn",  "유니콘"),
    PHOENIX(TroopType.PET, 5, "Phoenix",  "피닉스"),
    POISON_LIZARD(TroopType.PET, 6, "Poison Lizard",  "독마뱀"),
    DIGGY(TroopType.PET, 7, "Diggy",  "디기"),
    FROSTY(TroopType.PET, 8, "Frosty",  "프로스티"),
    SPIRIT_FOX(TroopType.PET, 9, "Spirit Fox", "스피릿 폭스"),
    ANGRY_JELLY(TroopType.PET, 10, "Angry Jelly", "앵그리 젤리"),

    UNKNOWN(TroopType.UNKNOWN, 99,"unknown", "알 수 없음");

    private final TroopType type;
    private final Integer order;
    private final String name;
    private final String koreanName;

    Troop(TroopType type, Integer order, String name, String koreanName) {
        this.type = type;
        this.order = order;
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
