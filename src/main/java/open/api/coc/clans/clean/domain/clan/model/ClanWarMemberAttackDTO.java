package open.api.coc.clans.clean.domain.clan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClanWarMemberAttackDTO {

    private Long warId;
    private Integer order;

    private String tag;

    private Integer stars;
    private Integer destructionPercentage;
    private Integer duration;

}
