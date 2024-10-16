package open.api.coc.clans.clean.domain.player.model;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.player.config.SiegeMachineConfig;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerSiegeMachine {

    private SiegeMachineConfig config; // 시즈머신 메타 정보

    private String name; // 이름

    private Integer level; // 현재 레벨
    private Integer maxLevel; // 최대 레벨

    private String village; // 제공 마을 유형 - home: 본 마을, builderBase: 장인기지

    private PlayerSiegeMachine(String name, Integer level, Integer maxLevel, String village) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.village = village;

        this.config = loadConfig();
    }

    private SiegeMachineConfig loadConfig() {
        return SiegeMachineConfig.findByName(this.name);
    }
    public boolean isVillageHome() {
        return Objects.equals("home", village);
    }
    public String getKoreanName() {
        return this.config.getKoreanName();
    }
    public int getOrder() {
        return this.config.getOrder();
    }


    public static class PlayerSiegeMachineBuilder {
        private String name;
        private Integer level;
        private Integer maxLevel;
        private String village = "home";

        public PlayerSiegeMachine build() {
            return new PlayerSiegeMachine(name, level, maxLevel, village);
        }

    }

}
