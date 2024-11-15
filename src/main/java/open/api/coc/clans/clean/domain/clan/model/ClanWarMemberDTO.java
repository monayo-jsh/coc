package open.api.coc.clans.clean.domain.clan.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
@AllArgsConstructor
public class ClanWarMemberDTO {

    private Long warId;
    private Integer mapPosition;

    private String tag;
    private String name;

    private YnType necessaryAttackYn;

    private List<ClanWarMemberAttackDTO> attacks;

}
