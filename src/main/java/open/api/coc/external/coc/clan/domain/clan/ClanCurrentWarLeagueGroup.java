package open.api.coc.external.coc.clan.domain.clan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCurrentWarLeagueGroup {

    private String state;

    @Setter
    private String season;

    private List<ClanWarLeague> clans;
    private List<ClanCurrentWarLeagueRound> rounds;

    @JsonIgnore
    public boolean isWarEnded() {
        return "ended".equals(state);
    }

    @JsonIgnore
    public boolean isWarNotEnded() {
        return !isWarEnded();
    }

    @JsonIgnore
    public boolean isNotInWar() {
        return "notInWar".equals(state);
    }
}
