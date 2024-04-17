package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCapitalRaidSeasonMemberRes {

    private String tag;
    private String name;
    private Integer attacks;
    private Integer attackLimit;
    private Integer bonusAttackLimit;

}
