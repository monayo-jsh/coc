package open.api.coc.external.coc.league;

import java.util.Optional;
import open.api.coc.external.coc.clan.domain.leagues.LabelList;

public interface LeagueApi {

    Optional<LabelList> findLeagues();
    Optional<LabelList> findLeagueTiers();

}
