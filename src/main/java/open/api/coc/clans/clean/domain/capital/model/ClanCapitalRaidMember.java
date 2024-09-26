package open.api.coc.clans.clean.domain.capital.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanCapitalRaidMember {

    private Long id; // 캐피탈 참여 고유키
    private String tag; // 플레이어 태그
    private String name; // 플레이어 이름
    private Integer attacks; // 플레이어 공격수
    private Integer attackLimit; // 플레이어 공격 가능수
    private Integer bonusAttackLimit; // 플레이어 보너스 수
    private Integer capitalResourcesLooted; // 플레이어 캐피탈 점수

    private Long raidId; // 캐피탈 고유키

    public static ClanCapitalRaidMember createNew(String tag, String name, Integer attacks, Integer attackLimit, Integer bonusAttackLimit, Integer capitalResourcesLooted, Long raidId) {
        return ClanCapitalRaidMember.builder()
                                    .tag(tag)
                                    .name(name)
                                    .attacks(attacks)
                                    .attackLimit(attackLimit)
                                    .bonusAttackLimit(bonusAttackLimit)
                                    .capitalResourcesLooted(capitalResourcesLooted)
                                    .raidId(raidId)
                                    .build();

    }

    public void assignIdIfAbsent(Long id) {
        // 도메인 모델에 아이디 매핑은 빈 상태에서만 1회 할당
        if (this.id == null){
            this.id = id;
        }
    }

    public void changeMemberInfo(Integer attacks, Integer attackLimit, Integer bonusAttackLimit, Integer capitalResourcesLooted) {
        this.attacks = attacks;
        this.attackLimit = attackLimit;
        this.bonusAttackLimit = bonusAttackLimit;
        this.capitalResourcesLooted = capitalResourcesLooted;
    }

}
