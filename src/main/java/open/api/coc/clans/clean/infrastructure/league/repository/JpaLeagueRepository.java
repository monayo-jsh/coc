package open.api.coc.clans.clean.infrastructure.league.repository;

import open.api.coc.clans.clean.infrastructure.league.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLeagueRepository extends JpaRepository<LeagueEntity, Integer> {
}
