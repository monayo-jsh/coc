package open.api.coc.clans.database.repository;

import java.util.List;
import open.api.coc.clans.database.entity.ClanAssignedPlayerEntity;
import open.api.coc.clans.database.entity.ClanAssignedPlayerPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanAssignedPlayerRepository extends JpaRepository<ClanAssignedPlayerEntity, ClanAssignedPlayerPKEntity> {

    @Query("select max(cap.seasonDate) from tb_clan_assigned_player cap where cap.clanTag = :clanTag")
    String findLatestSeasonDateByClanTag(String clanTag);

    List<ClanAssignedPlayerEntity> findByClanTagAndSeasonDate(String clanTag, String SeasonDate);


}
