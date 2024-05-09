package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClanContentRequest {

    @JsonProperty("tag")
    private final String tag;

    @JsonProperty("clan_war_yn")
    private final String clanWarYn;

    @JsonProperty("clan_war_league_yn")
    private final String clanWarLeagueYn;

    @JsonProperty("clan_capital_yn")
    private final String clanCapitalYn;

    @JsonProperty("clan_war_parallel_yn")
    private final String clanWarParallelYn;

}
