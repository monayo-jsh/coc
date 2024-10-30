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

    @Query("select max(clap.id.seasonDate) from ClanLeagueAssignedPlayerEntity clap")
    String findLatestLeagueSeasonDate();

    @Query("select clap from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate and clap.clan.tag = :clanTag")
    List<ClanLeagueAssignedPlayerEntity> findClanLeagueAssignedPlayersByClanTagAndSeasonDate(String clanTag, String seasonDate);

    @Query(value = "select new open.api.coc.clans.database.entity.clan.ClanAssignedPlayerDTO(clap.id.seasonDate, clap.id.playerTag, player.name, clan) "
        + " from ClanLeagueAssignedPlayerEntity clap "
        + " join PlayerEntity player on player.playerTag = clap.id.playerTag "
        + " join ClanEntity clan on clan.tag = clap.clan.tag "
        + " join ClanBadgeEntity cb on cb.tag = clan.tag "
        + " where clap.id.seasonDate = :seasonDate ")
    List<ClanAssignedPlayerDTO> findBySeasonDate(String seasonDate);

    @Modifying
    @Query("delete from ClanLeagueAssignedPlayerEntity clap where clap.id.seasonDate = :seasonDate")
    void deleteAllBySeasonDate(String seasonDate);

}
