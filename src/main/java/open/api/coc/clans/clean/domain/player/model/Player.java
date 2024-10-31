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
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.external.coc.config.HeroConfig;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Player {

    private String tag; // 태그
    private String name; // 이름

    private YnType supportYn; // 지원계정 여부 - Y: 지원계정, N: 아님

    private Integer expLevel; // 레벨
    private Integer townHallLevel; // 타운홀 레벨

    private Integer trophies; // 현재 트로피
    private Integer bestTrophies; // 최대 트로피

    private Integer attackWins; // 공격 성공수
    private Integer defenseWins; // 방어 성공수

    private Integer warStars; // 전쟁 획득 별

    private Integer leagueId; // 현재 리그 고유키

    private String role; // 가입 클랜 직위 - leader: 대표, coLeader: 공동대표, admin: 장로, member: 일반
    private String warPreference; // 가입 클랜 전쟁 선호도 - in: 참가, out: 불참

    private String clanTag; // 가입 클랜 태그

    private Integer donations; // 지원수
    private Integer donationsReceived; // 지원받은수

    private List<PlayerHero> heroes; // 영웅 목록
    private List<PlayerHeroEquipment> heroEquipments; // 영웅장비 목록

    private List<PlayerSpell> spells; // 마법 목록
    private List<PlayerPet> pets; // 펫 목록
    private List<PlayerSiegeMachine> siegeMachines; // 시즈머신 목록

    private Integer clanGamePoint; // 클랜게임 포인트

    private PlayerAchievementDonationInfo donationInfo; // 업적 지원 정보

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

    public void changeSupportType(YnType supportYn) {
        if (YnType.Y.equals(supportYn)) {
            changeSupportPlayer();
            return;
        }

        changeNormalPlayer();
    }

    public void changeNormalPlayer() {
        // 일반 계정 설정
        this.supportYn = YnType.N;
    }

    public void changeSupportPlayer() {
        this.supportYn = YnType.Y;
    }

    public void changeInfo(Player latestPlayer) {
        changePlayer(latestPlayer);
        changeHero(latestPlayer.getHeroes());
        changeHeroEquipments(latestPlayer.getHeroEquipments());
        changeSpells(latestPlayer.getSpells());
        changePets(latestPlayer.getPets());
        changeSiegeMachines(latestPlayer.getSiegeMachines());
    }

    private void changeSiegeMachines(List<PlayerSiegeMachine> newSiegeMachines) {
        Map<String, PlayerSiegeMachine> origSiegeMachineMap = this.siegeMachines.stream()
                                                                                .collect(Collectors.toMap(PlayerSiegeMachine::getName, siegeMachine -> siegeMachine));

        for(PlayerSiegeMachine newSiegeMachine : newSiegeMachines) {
            // 기존 정보 조회
            PlayerSiegeMachine findOrigSiegeMachine = origSiegeMachineMap.get(newSiegeMachine.getName());
            if (findOrigSiegeMachine == null) {
                // 기존 정보 없으면 추가
                this.siegeMachines.add(newSiegeMachine);
                continue;
            }

            // 정보 현행화
            findOrigSiegeMachine.changeInfo(newSiegeMachine);
        }
    }

    private void changePets(List<PlayerPet> newPets) {
        Map<String, PlayerPet> origPetMap = this.pets.stream()
                                                     .collect(Collectors.toMap(PlayerPet::getName, pet -> pet));

        for(PlayerPet newPet : newPets) {
            // 기존 정보 조회
            PlayerPet findOrigPet = origPetMap.get(newPet.getName());
            if (findOrigPet == null) {
                // 기존 정보 없으면 추가
                this.pets.add(newPet);
                continue;
            }

            // 정보 현행화
            findOrigPet.changeInfo(newPet);
        }
    }

    private void changeSpells(List<PlayerSpell> newSpells) {
        Map<String, PlayerSpell> origSpellMap = this.spells.stream()
                                                           .collect(Collectors.toMap(PlayerSpell::getName, spell -> spell));

        for(PlayerSpell newSpell : newSpells) {
            // 기존 정보 조회
            PlayerSpell findOrigSpell = origSpellMap.get(newSpell.getName());
            if (findOrigSpell == null) {
                // 기존 정보 없으면 추가
                this.spells.add(newSpell);
                continue;
            }

            // 정보 현행화
            findOrigSpell.changeInfo(newSpell);
        }
    }

    private void changeHeroEquipments(List<PlayerHeroEquipment> newHeroEquipments) {
        Map<String, PlayerHeroEquipment> origHeroEquipmentMap = this.heroEquipments.stream()
                                                                                   .collect(Collectors.toMap(PlayerHeroEquipment::getName, heroEquipment -> heroEquipment));

        for(PlayerHeroEquipment newHeroEquipment : newHeroEquipments) {
            // 기존 정보 조회
            PlayerHeroEquipment findOrigHeroEquipment = origHeroEquipmentMap.get(newHeroEquipment.getName());
            if (findOrigHeroEquipment == null) {
                // 기존 정보 없으면 추가
                this.heroEquipments.add(newHeroEquipment);
                continue;
            }

            // 정보 현행화
            findOrigHeroEquipment.changeInfo(newHeroEquipment);
        }
    }

    private void changeHero(List<PlayerHero> newHeroes) {
        Map<String, PlayerHero> origHeroMap = this.heroes.stream()
                                                         .collect(Collectors.toMap(PlayerHero::getName, hero -> hero));

        for(PlayerHero latestHero : newHeroes) {
            // 기존 정보 조회
            PlayerHero findOrigHero = origHeroMap.get(latestHero.getName());
            if (findOrigHero == null) {
                // 기존 정보 없으면 추가
                this.heroes.add(latestHero);
                continue;
            }

            // 정보 현행화
            findOrigHero.changeInfo(latestHero);
        }
    }

    private void changePlayer(Player latestPlayer) {
        // 이름
        this.name = latestPlayer.getName();

        // 레벨
        this.expLevel = latestPlayer.getExpLevel();
        this.townHallLevel = latestPlayer.getTownHallLevel();

        // 리그
        this.leagueId = latestPlayer.getLeagueId();

        // 트로피
        this.trophies = latestPlayer.getTrophies();
        this.bestTrophies = latestPlayer.getBestTrophies();

        // 지원
        this.donations = latestPlayer.getDonations();
        this.donationsReceived = latestPlayer.getDonationsReceived();

        // 공/방 성공
        this.attackWins = latestPlayer.getAttackWins();
        this.defenseWins = latestPlayer.getDefenseWins();

        // 클랜
        this.clanTag = latestPlayer.getClanTag();
        this.warStars = latestPlayer.getWarStars(); // 전쟁 획득 별
        this.role = latestPlayer.getRole(); // 클랜 직급
        this.warPreference = latestPlayer.getWarPreference(); // 전쟁 선호도
    }

    public boolean isRecoding(Player originPlayer) {
        // 공격 성공 수치 다른 경우 기록
        if (!Objects.equals(this.attackWins, originPlayer.getAttackWins())) return true;

        // 방어 성공 수치 다른 경우 기록
        if (!Objects.equals(this.defenseWins, originPlayer.getDefenseWins())) return true;

        // 트로피 점수 다른 경우 기록
        if (!Objects.equals(this.trophies, originPlayer.getTrophies())) {

            if (this.trophies - originPlayer.getTrophies() > 80) {
                // 플레이어 트로피 값이 초기화되지 않은 상태로 넘어온 경우 기록하지 않도록 예외 처리 기준 설정
                return false;
            }

            return true;
        }

        // 그 외는 기록하지 않음
        return false;
    }

    public boolean isNotInLeague() {
        return !isInLeague();
    }

    // 기본값 설정을 위한 빌더 객체
    public static class PlayerBuilder {
        private String tag; // 플레이어 태그

        private List<PlayerHero> heroes = new ArrayList<>(); // 영웅 목록
        private List<PlayerHeroEquipment> heroEquipments = new ArrayList<>(); // 영웅장비 목록

        private List<PlayerSpell> spells = new ArrayList<>(); // 마법 목록
        private List<PlayerPet> pets = new ArrayList<>(); // 펫 목록
        private List<PlayerSiegeMachine> siegeMachines = new ArrayList<>(); // 시즈머신 목록

        public Map<String, PlayerHeroEquipment> getHeroEquipmentMap() {
            return this.heroEquipments.stream()
                                      .collect(Collectors.toMap(PlayerHeroEquipment::getName, heroEquipment -> heroEquipment));
        }

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

        public void mappingPlayerTag() {
            this.heroes.forEach(hero -> hero.assignPlayerTagIfAbsent(tag));
            this.heroEquipments.forEach(heroEquipment -> heroEquipment.assignPlayerTagIfAbsent(tag));
            this.spells.forEach(spell -> spell.assignPlayerTagIfAbsent(tag));
            this.pets.forEach(pet -> pet.assignPlayerTagIfAbsent(tag));
            this.siegeMachines.forEach(siegeMachine -> siegeMachine.assignPlayerTagIfAbsent(tag));
        }
    }
}
