package open.api.coc.clans.database.repository.common;

import open.api.coc.clans.database.entity.league.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueEntity, Integer> {
}
