package open.api.coc.external.coc.clan.domain.capital;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalRaidSeason {

    private String state;
    private String startTime;
    private String endTime;
    private List<ClanCapitalRaidSeasonMember> members = new ArrayList<>();

}
