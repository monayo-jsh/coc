package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCapitalRaidSeasonMemberResponse {

    private String tag;
    private String name;
    private Integer attacks;
    private Integer attackLimit;
    private Integer bonusAttackLimit;
    private Integer capitalResourcesLooted;

}
