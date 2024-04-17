package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarAttackRes {

    private String attackerTag;
    private String defenderTag;
    private Integer destructionPercentage;
    private Integer duration;
    private Integer stars;
    private Integer order;

}
