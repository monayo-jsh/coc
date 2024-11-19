package open.api.coc.clans.clean.domain.clan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClanWarMemberAttackDTO {

    private Long warId;
    private String tag;
    private Integer order;

    private Integer stars;
    private Integer destructionPercentage;
    private Integer duration;

}
