package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCapitalRaidSeasonResponse {

    private String state;

    private long startTime;
    private long endTime;

    private List<ClanCapitalRaidSeasonMemberResponse> members;

}
