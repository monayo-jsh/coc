package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarResponse {

    private Long warId;

    private String clanTag;

    @Setter
    private String clanName;

    private String state;

    private String battleType;
    private Integer teamSize;

    private Integer attacksPerMember;

    private long startTime;
    private long endTime;

    @Setter
    private List<ClanWarMemberResponse> members;

}
