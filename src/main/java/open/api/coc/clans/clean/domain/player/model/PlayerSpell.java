package open.api.coc.clans.clean.domain.player.model;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.player.config.SpellConfig;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerSpell {

    private SpellConfig config; // 마법 메타 정보

    private String playerTag; // 플레이어 태그
    private String name; // 이름

    private String village; // 제공 마을 유형 - home: 본 마을, builderBase: 장인기지

    private Integer level; // 현재 레벨
    private Integer maxLevel; // 최대 레벨

    private PlayerSpell(String name, Integer level, Integer maxLevel, String village) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.village = village;

        this.config = loadConfig();
    }

    public boolean isVillageHome() {
        return Objects.equals("home", village);
    }

    private SpellConfig loadConfig() {
        return SpellConfig.findByName(this.name);
    }
    public String getType() {
        return this.config.getType().name();
    }
    public String getKoreanName() {
        return this.config.getKoreanName();
    }

    public Integer getOrder() {
        return this.config.getOrder();
    }

    public void assignPlayerTagIfAbsent(String playerTag) {
        if (this.playerTag == null) {
            this.playerTag = playerTag;
        }
    }

    public static class PlayerSpellBuilder {

        private String playerTag;
        private String name;
        private Integer level;
        private Integer maxLevel;
        private String village = "home";

        public PlayerSpell build() {
            return new PlayerSpell(name, level, maxLevel, village);
        }

    }
}
