package open.api.coc.external.coc.clan.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalRaidSeason {

    private List<ClanCapitalRaidSeasonMember> members = new ArrayList<>();

}
