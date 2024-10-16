package open.api.coc.clans.clean.domain.player.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.external.coc.config.HeroConfig;
import org.springframework.util.CollectionUtils;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerHero {

    private final HeroConfig config; // 영웅 메타 정보

    private String name; // 이름
    private Integer level; // 현재 레벨
    private Integer maxLevel; // 최대 레벨
    private String village; // 제공 마을 유형 - home: 본 마을, builderBase: 장인기지

    private List<PlayerHeroEquipment> equipments; // 현재 착용중인 장비 목록

    private PlayerHero(String name, Integer level, Integer maxLevel, String village) {
        this.name = name;
        this.level = level;
        this.maxLevel = maxLevel;
        this.village = village;
        this.equipments = new ArrayList<>();

        this.config = loadConfig();
    }

    public boolean isVillageHome() {
        return Objects.equals("home", village);
    }
    public boolean isNotVillageHome() {
        return !isVillageHome();
    }

    private HeroConfig loadConfig() {
        return HeroConfig.findByName(this.name);
    }

    public int getCode() {
        return this.config.getCode();
    }

    public String getKoreanName() {
        return this.config.getKoreanName();
    }

    public String getKoreanShortName() {
        return this.config.getKoreanShortName();
    }

    public void changeEquipments(List<PlayerHeroEquipment> playerHeroEquipments) {
        if (CollectionUtils.isEmpty(playerHeroEquipments)) return;
        this.equipments.clear();
        this.equipments.addAll(new ArrayList<>(playerHeroEquipments));
    }

    public static class PlayerHeroBuilder {
        private String name;
        private Integer level; // 현재 레벨
        private Integer maxLevel; // 최대 레벨
        private String village = "home"; // 제공 마을 유형 - home: 본 마을, builderBase: 장인기지

        public PlayerHero build() {
            return new PlayerHero(name, level, maxLevel, village);
        }

    }
}
