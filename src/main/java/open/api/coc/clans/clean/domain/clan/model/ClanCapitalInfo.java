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

}
