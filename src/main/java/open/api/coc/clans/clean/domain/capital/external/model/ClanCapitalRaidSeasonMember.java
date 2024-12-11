package open.api.coc.clans.clean.domain.capital.external.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanCapitalRaidSeasonMember {

    private String tag; // 플레이어 태그
    private String name; // 플레이어 이름
    private Integer attacks; // 플레이어 공격수
    private Integer attackLimit; // 플레이어 공격 가능수
    private Integer bonusAttackLimit; // 플레이어 보너스 수
    private Integer capitalResourcesLooted; // 플레이어 캐피탈 점수

}
