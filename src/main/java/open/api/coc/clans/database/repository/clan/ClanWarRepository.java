package open.api.coc.clans.database.repository.clan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.domain.ranking.ClanWarCount;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanWarRepository extends JpaRepository<ClanWarEntity, Long> {

    @Query("select clanWar from ClanWarEntity clanWar where clanWar.startTime between :startTime and :endTime order by clanWar.warId")
    List<ClanWarEntity> findAllByPeriod(LocalDateTime startTime, LocalDateTime endTime);

    @Query("select clanWar from ClanWarEntity clanWar where clanWar.clanTag = :clanTag and clanWar.startTime = :startTime")
    Optional<ClanWarEntity> findByClanTagAndStartTime(String clanTag, LocalDateTime startTime);

    @Query("select clanWar from ClanWarEntity clanWar where clanWar.endTime < :now and clanWar.state != :state")
    List<ClanWarEntity> findAfterEndTimeAndNotState(LocalDateTime now, String state);

    @Query("SELECT  max(clanWarMemberAttack.id.tag) as tag, "
        + "         max(player.name) as name, "
        + "         sum(clanWarMemberAttack.stars) as score, "
        + "         sum(clanWarMemberAttack.destructionPercentage) as destructionPercentage, "
        + "         avg(clanWarMemberAttack.duration) as duration, "
        + "         max(clan.tag) as clanTag, "
        + "         max(clan.name) as clanName"
        + " FROM ClanWarEntity clanWar"
        + " JOIN ClanWarMemberEntity clanWarMember on clanWarMember.id.warId = clanWar.warId"
        + " JOIN ClanWarMemberAttackEntity clanWarMemberAttack on clanWarMemberAttack.id.warId = clanWarMember.id.warId and clanWarMemberAttack.id.tag = clanWarMember.id.tag"
        + " JOIN PlayerEntity player on player.playerTag = clanWarMember.id.tag"
        + " JOIN ClanEntity clan on clan.tag = clanWar.clanTag "
        + " WHERE clanWar.type = :type "
        + " AND clanWar.state = 'warCollected'"
        + " AND clanWar.startTime between :startTime and :endTime"
        + " group by clanWarMemberAttack.id.tag"
        + " order by score desc, destructionPercentage desc, duration")
    List<RankingHallOfFame> selectRankingClanWarStars(ClanWarType type, LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    @Query("SELECT  max(clanWarMemberAttack.id.tag) as tag, "
        + "         max(player.name) as name, "
        + "         sum(clanWarMemberAttack.stars) as score, "
        + "         sum(clanWarMemberAttack.destructionPercentage) as destructionPercentage, "
        + "         avg(clanWarMemberAttack.duration) as duration, "
        + "         max(clan.tag) as clanTag, "
        + "         max(clan.name) as clanName"
        + " FROM ClanWarEntity clanWar"
        + " JOIN ClanWarMemberEntity clanWarMember on clanWarMember.id.warId = clanWar.warId"
        + " JOIN ClanWarMemberAttackEntity clanWarMemberAttack on clanWarMemberAttack.id.warId = clanWarMember.id.warId and clanWarMemberAttack.id.tag = clanWarMember.id.tag"
        + " JOIN PlayerEntity player on player.playerTag = clanWarMember.id.tag"
        + " JOIN ClanEntity clan on clan.tag = clanWar.clanTag "
        + " WHERE clanWar.type = :type "
        + " AND clanWar.clanTag = :clanTag "
        + " AND clanWar.state = 'warCollected'"
        + " AND clanWar.startTime between :startTime and :endTime"
        + " group by clanWarMemberAttack.id.tag"
        + " order by score desc, destructionPercentage desc, duration")
    List<RankingHallOfFame> selectRankingClanWarStarsByClanTag(ClanWarType type, LocalDateTime startTime, LocalDateTime endTime, String clanTag, Pageable pageable);

    @Query("select clanWar"
        + " from ClanWarEntity clanWar"
        + " join ClanWarMemberEntity clanWarMember on clanWarMember.clanWar.warId = clanWar.warId "
        + " left join ClanWarMemberAttackEntity clanWarMemberAttack on clanWarMemberAttack.id.warId = clanWarMember.id.warId and clanWarMemberAttack.id.tag = clanWarMember.id.tag "
        + " where clanWar.warId = :warId ")
    Optional<ClanWarEntity> findByWarId(Long warId);

    @Query("select clanWar.clanTag as tag, count(clanWar.clanTag) as count "
        + " from ClanWarEntity clanWar"
        + " where clanWar.type = :type "
        + " and clanWar.startTime between :startTime and :endTime "
        + " group by clanWar.clanTag "
    )
    List<ClanWarCount> selectClanWarCount(ClanWarType type, LocalDateTime startTime, LocalDateTime endTime);
}
