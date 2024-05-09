package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanContentResponse {

    private String tag;
    private String clanWarYn;
    private String clanWarLeagueYn;
    private String clanCapitalYn;
    private String clanWarParallelYn;

}
