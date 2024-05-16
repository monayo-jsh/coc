package open.api.coc.clans.database.repository.clan;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanAssignedPlayerRepository extends JpaRepository<ClanAssignedPlayerEntity, ClanAssignedPlayerPKEntity> {

    @Query("select max(cap.id.seasonDate) from tb_clan_assigned_player cap where cap.clanTag = :clanTag")
    String findLatestSeasonDateByClanTag(String clanTag);

    @Query("select cap from tb_clan_assigned_player cap where cap.clanTag = :clanTag and cap.id.seasonDate = :seasonDate")
    List<ClanAssignedPlayerEntity> findByClanTagAndSeasonDate(String clanTag, String seasonDate);


}
