package open.api.coc.clans.clean.infrastructure.league.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.league.repository.LeagueRepository;
import open.api.coc.clans.clean.infrastructure.league.persistence.mapper.LeagueEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LeagueCoreRepository implements LeagueRepository {

    private final JpaLeagueRepository jpaLeagueRepository;

    private final LeagueEntityMapper leagueMapper;

    @Override
    public List<League> findAll() {
        return jpaLeagueRepository.findAll()
                                  .stream()
                                  .map(leagueMapper::toDomain)
                                  .toList();
    }
}
