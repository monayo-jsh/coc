package open.api.coc.clans.database.repository.clan;

import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanLeagueWarRepository extends JpaRepository<ClanLeagueWarEntity, Long> {

    @Query("select clanLeagueWar from ClanLeagueWarEntity clanLeagueWar where clanLeagueWar.clanTag = :clanTag and clanLeagueWar.season = :season")
    Optional<ClanLeagueWarEntity> findByClanTagAndSeason(String clanTag, String season);

    @Modifying
    @Query("update ClanLeagueWarEntity clanLeagueWar set clanLeagueWar.warLeague = :warLeague where clanLeagueWar.leagueWarId = :leagueWarId")
    void update(Long leagueWarId, String warLeague);

}
