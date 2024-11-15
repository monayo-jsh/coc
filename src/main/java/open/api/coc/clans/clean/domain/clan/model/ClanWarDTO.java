package open.api.coc.clans.clean.domain.clan.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import open.api.coc.clans.database.entity.clan.ClanWarType;

@Getter
@AllArgsConstructor
public class ClanWarDTO {

    private Long warId;
    private String clanName;
    private String state;
    private ClanWarType type;
    private String battleType;
    private LocalDateTime preparationStartTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer attacksPerMember;
    private String warTag;

    private List<ClanWarMemberDTO> members;

}
