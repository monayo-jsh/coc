package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarAttack {

    private String attackerTag;
    private String defenderTag;
    private Integer destructionPercentage;
    private Integer duration;
    private Integer stars;
    private Integer order;

}
