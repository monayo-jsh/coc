package open.api.coc.clans.clean.domain.player.external.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerResponse {

    private String tag; // 태그
    private String name; // 이름

    private Integer expLevel; // 레벨
    private Integer townHallLevel; // 타운홀 레벨
    private Integer townHallWeaponLevel; // 타운홀 무기 레벨

    private Integer trophies; // 현재 트로피
    private Integer bestTrophies; // 최고 트로피

    private Integer donations; // 현재 지원수
    private Integer donationsReceived; // 현재 지원받은수

    private Integer attackWins; // 공격 성공수
    private Integer defenseWins; // 방어 성공수

    private Integer warStars; // 전쟁 획득 별

    private PlayerLeagueResponse league; // 현재 리그 정보

    private String role; // 가입 클랜 직위 - leader: 대표, coLeader: 공동대표, admin: 장로, member: 일반
    private String warPreference; // 가입 클랜 전쟁 선호도 - in: 참가, out: 불참
    private PlayerClanResponse clan; // 가입 클랜 정보

    private List<PlayerHeroResponse> heroes; // 영웅 목록
    private List<PlayerHeroEquipmentResponse> heroEquipment; // 영웅장비 목록

    private List<PlayerTroopResponse> troops; // 유닛 목록 (유닛, 펫, 시즈머신)
    private List<PlayerSpellResponse> spells; // 마법 목록

    public List<PlayerHeroEquipmentResponse> getWearHeroEquipments() {
        // 착용중인 영웅 장비를 반환한다.
        return this.heroes.stream()
                          .flatMap(PlayerHeroResponse::getEquipmentsStream)
                          .toList();
    }

    // 기본값 설정을 위한 빌더 객체
    public static class PlayerResponseBuilder {
        private List<PlayerHeroResponse> heroes = new ArrayList<>(); // 영웅 목록
        private List<PlayerHeroEquipmentResponse> heroEquipment = new ArrayList<>(); // 영웅장비 목록

        private List<PlayerTroopResponse> troops = new ArrayList<>(); // 유닛 목록 (유닛, 펫, 시즈머신)
        private List<PlayerSpellResponse> spells = new ArrayList<>(); // 마법 목록
    }
}
