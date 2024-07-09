package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@AllArgsConstructor
public class ClanWarMemberAttackResponse {

    private Long warId;

    private Integer order;

    private String tag;

    private Integer stars;

    private Integer destructionPercentage;

    private Integer duration;

}
