package open.api.coc.clans.clean.domain.league.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.league.model.League;

public interface LeagueRepository {

    List<League> findAll();

}
