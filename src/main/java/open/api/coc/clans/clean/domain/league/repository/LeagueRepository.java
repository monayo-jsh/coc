package open.api.coc.clans.clean.domain.league.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.league.model.League;

public interface LeagueRepository {

    List<League> findAll();
    List<League> findAllByIds(List<Integer> leagueIds);

    Optional<League> findById(Integer leagueId);

}
