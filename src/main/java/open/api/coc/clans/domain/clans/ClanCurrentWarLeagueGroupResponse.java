package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCurrentWarLeagueGroupResponse {

    private String state;
    private String season;
    private List<ClanResponse> clans;
    private List<ClanCurrentWarLeagueRoundResponse> rounds;

}
