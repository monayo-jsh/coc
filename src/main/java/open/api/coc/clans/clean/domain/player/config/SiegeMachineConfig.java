package open.api.coc.clans.clean.domain.player.config;

import java.util.Arrays;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.TroopType;

@Getter
public enum SiegeMachineConfig {

    WALL_WRECKER(TroopType.SIEGE_MACHINE, 1, "Wall Wrecker", "파괴 전차"),
    BATTLE_BLIMP(TroopType.SIEGE_MACHINE, 2, "Battle Blimp", "전투 비행선"),
    STONE_SLAMMER(TroopType.SIEGE_MACHINE, 3, "Stone Slammer", "바위 비행선"),
    SIEGE_BARRACKS(TroopType.SIEGE_MACHINE, 4, "Siege Barracks", "시즈 훈련소"),
    LOG_LAUNCHER(TroopType.SIEGE_MACHINE, 5, "Log Launcher", "통나무 발사기"),
    FLAME_FLINGER(TroopType.SIEGE_MACHINE, 6, "Flame Flinger", "화염 투척기"),
    BATTLE_DRILL(TroopType.SIEGE_MACHINE, 7, "Battle Drill", "배틀 드릴"),

    UNKNOWN(TroopType.UNKNOWN, 99,"unknown", "알 수 없음");

    private final TroopType type;
    private final Integer order;
    private final String name;
    private final String koreanName;

    SiegeMachineConfig(TroopType type, Integer order, String name, String koreanName) {
        this.type = type;
        this.order = order;
        this.name = name;
        this.koreanName = koreanName;
    }

    public static SiegeMachineConfig findByName(String troopName) {
        return Arrays.stream(values())
                     .filter(troop -> Objects.equals(troop.getName(), troopName))
                     .findFirst()
                     .orElse(UNKNOWN);
    }

    public boolean isSiegeMachine() {
        return this.type.equals(TroopType.SIEGE_MACHINE);
    }
}
