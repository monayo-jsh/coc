package open.api.coc.clans.clean.infrastructure.league.persistence.repository;

import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLeagueRepository extends JpaRepository<LeagueEntity, Integer> {
}
