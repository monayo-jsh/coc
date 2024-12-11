package open.api.coc.clans.clean.domain.player.model;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.external.coc.config.HeroConfig;
import open.api.coc.external.coc.config.HeroEquipmentConfig;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerHeroEquipment {

    private final HeroEquipmentConfig config; // 영웅 장비 메타 정보

    private String playerTag; // 플레이어 태그
    private String name; // 장비 이름

    private String village; // 제공 마을 유형 - home: 본 마을, builderBase: 장인기지

    private Integer level; // 현재 레벨
    private Integer maxLevel; // 최대 레벨

    private YnType wearYn; // 영웅 장비 착용 여부 - Y: 착용, N: 안함

    private PlayerHeroEquipment(String playerTag, String name, Integer level, Integer maxLevel, String village, YnType wearYn) {
        this.playerTag = playerTag;
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.village = village;
        this.wearYn = wearYn;

        this.config = loadConfig();
    }

    private HeroEquipmentConfig loadConfig() {
        return HeroEquipmentConfig.findByName(this.name);
    }

    public boolean isVillageHome() {
        return Objects.equals("home", village);
    }

    public int getCode() {
        return this.config.getCode();
    }

    public String getKoreanName() {
        return this.config.getKoreanName();
    }

    public String getRarity() {
        return this.config.getRarity();
    }
    public HeroConfig getHeroConfig() {
        return this.config.getHero();
    }
    public String getHeroName() {
        return this.getHeroConfig().getName();

    }
    public Integer getHeroCode() {
        return this.getHeroConfig().getCode();
    }

    public boolean isWear() {
        return YnType.Y.equals(this.wearYn);
    }

    public void wear() {
        // 장비를 착용
        this.wearYn = YnType.Y;
    }

    public void assignPlayerTagIfAbsent(String playerTag) {
        if (this.playerTag == null) {
            this.playerTag = playerTag;
        }
    }

    public void changeInfo(PlayerHeroEquipment newHeroEquipment) {
        this.level = newHeroEquipment.getLevel();
        this.maxLevel = newHeroEquipment.getMaxLevel();

        this.wearYn = newHeroEquipment.getWearYn();
    }

    public static class PlayerHeroEquipmentBuilder {

        private String playerTag;
        private String name;

        private String village = "home"; // 제공 마을 유형 - home: 본 마을, builderBase: 장인기지

        private Integer level;
        private Integer maxLevel;

        private YnType wearYn = YnType.N;

        public PlayerHeroEquipment build() {
            return new PlayerHeroEquipment(playerTag, name, level, maxLevel, village, wearYn);
        }

    }
}
