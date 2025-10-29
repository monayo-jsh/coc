package open.api.coc.clans.database.repository.clan;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerDTO;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPK;
import open.api.coc.clans.database.entity.clan.ClanLeagueAssignedPlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanLeagueAssignedPlayerRepository extends JpaRepository<ClanLeagueAssignedPlayerEntity, ClanAssignedPlayerPK> {

    @Query("select clap from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate and clap.clan.tag = :clanTag")
    List<ClanLeagueAssignedPlayerEntity> findClanLeagueAssignedPlayersByClanTagAndSeasonDate(String clanTag, String seasonDate);

    @Modifying
    @Query("delete from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate")
    void deleteAllBySeasonDate(String seasonDate);

}
