package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LeagueWar {
    private String state;
    private Integer teamSize;
    private String preparationStartTime;
    private String startTime;
    private String endTime;
    private LeagueWarClan clan;
    private LeagueWarClan opponent;
    private String warStartTime;

}
