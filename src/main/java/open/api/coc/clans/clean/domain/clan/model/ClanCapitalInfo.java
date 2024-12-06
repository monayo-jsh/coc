package open.api.coc.clans.clean.domain.clan.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanCapitalInfo {

    private Integer capitalHallLevel; // 캐피탈 타운홀 레벨
    private Integer tier; // 캐피탈 티어

    public void changeHallLevel(Integer capitalHallLevel) {
        this.capitalHallLevel = capitalHallLevel;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class ClanCapitalInfoBuilder {
        private Integer tier = -1;
    }
}
