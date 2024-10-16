package open.api.coc.clans.clean.domain.player.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.config.HeroConfig;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Player {

    private String tag; // 플레이어 태그
    private String name; // 플레이어 이름

    private String supportYn; // 지원계정 여부 - Y: 지원계정, N: 아님

    private Integer expLevel; // 플레이어 레벨
    private Integer townHallLevel; // 플레이어 타운홀 레벨

    private Integer trophies; // 플레이어 현재 트로피
    private Integer bestTrophies; // 플레이어 최대 트로피

    private Integer attackWins; // 플레이어 공격 성공수
    private Integer defenseWins; // 플레이어 방어 성공수

    private Integer warStars; // 플레이어 전쟁 획득 별

    private Integer leagueId; // 현재 리그 고유키

    private String role; // 가입 클랜 직위 - leader: 대표, coLeader: 공동대표, admin: 장로, member: 일반
    private String warPreference; // 가입 클랜 전쟁 선호도 - in: 참가, out: 불참

    private String clanTag; // 가입 클랜 태그

    private Integer donations; // 플레이어 지원수
    private Integer donationsReceived; // 플레이어 지원받은수

    private List<PlayerHero> heroes; // 영웅 목록
    private List<PlayerHeroEquipment> heroEquipments; // 영웅장비 목록

    private List<PlayerSpell> spells; // 마법 목록
    private List<PlayerPet> pets; // 펫 목록
    private List<PlayerSiegeMachine> siegeMachines; // 시즈머신 목록

    public boolean isJoinedClan() {
        return StringUtils.hasText(this.clanTag);
    }

    public boolean isInLeague() {
        return Objects.nonNull(this.leagueId);
    }

    public Integer getHeroTotalLevel() {
        return this.heroes.stream()
                          .filter(PlayerHero::isVillageHome)
                          .mapToInt(PlayerHero::getLevel)
                          .sum();
    }

    public Integer getHeroTotalMaxLevel() {
        return this.heroes.stream()
                          .filter(PlayerHero::isVillageHome)
                          .mapToInt(PlayerHero::getMaxLevel)
                          .sum();
    }

    // 기본값 설정을 위한 빌더 객체
    public static class PlayerBuilder {
        private List<PlayerHero> heroes = new ArrayList<>(); // 영웅 목록
        private List<PlayerHeroEquipment> heroEquipments = new ArrayList<>(); // 영웅장비 목록

        private List<PlayerSpell> spells = new ArrayList<>(); // 마법 목록
        private List<PlayerPet> pets = new ArrayList<>(); // 펫 목록
        private List<PlayerSiegeMachine> siegeMachines = new ArrayList<>(); // 시즈머신 목록

        public void mappingHeroWearEquipments() {
            Map<HeroConfig, List<PlayerHeroEquipment>> wearHeroEquipmentMap = makeWearHeroEquipmentMap();

            for(PlayerHero hero : heroes) {
                hero.changeEquipments(wearHeroEquipmentMap.get(hero.getConfig()));
            }
        }

        private Map<HeroConfig, List<PlayerHeroEquipment>> makeWearHeroEquipmentMap() {
            return heroEquipments.stream()
                                 .filter(PlayerHeroEquipment::isWear)
                                 .collect(Collectors.groupingBy(PlayerHeroEquipment::getHeroConfig));
        }
    }
}
