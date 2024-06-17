package open.api.coc.clans.database.repository.player;

import java.util.List;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import open.api.coc.clans.database.entity.player.RankingHeroEquipment;
import open.api.coc.clans.domain.ranking.RankingHallOfFame;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, String> {

    @Query("select p"
        + " from PlayerEntity p"
        + " left join fetch LeagueEntity l on l.id = p.league.id"
        + " left join fetch ClanEntity c on c.tag = p.clan.tag"
        + " left join fetch ClanBadgeEntity cb on cb.tag = c.tag"
    )
    List<PlayerEntity> findAll();

    @Query("select p"
        + " from PlayerEntity p"
        + " left join fetch LeagueEntity l on l.id = p.league.id"
        + " left join fetch ClanEntity c on c.tag = p.clan.tag"
        + " left join fetch ClanBadgeEntity cb on cb.tag = c.tag"
        + " where p.supportYn = :supportYn"
    )
    List<PlayerEntity> findAllBySupportYn(YnType supportYn);

    @Query("select p.playerTag from PlayerEntity p")
    List<String> findAllPlayerTag();

    @Query("select p.playerTag from PlayerEntity p")
    List<String> findAllPlayersNotIn(List<String> playerTags);

    @Query(
        nativeQuery = true,
        value =
              " select m.*,"
            + "        sum(m.count) over(partition by m.heroName) totalCount"
            + " from ("
            + "  select target_hero_name as heroName, equipments, count(equipments) as count"
            + "  from ("
            + "   select player_tag, target_hero_name, array_agg(name) as equipments"
            + "   from ("
            + "    select player_tag, target_hero_name, name"
            + "    from tb_player_hero_equipment"
            + "    where player_tag in (:playerTags)"
            + "    and wear_yn = 'Y'"
            + "    order by player_tag, target_hero_name, name"
            + "   )"
            + "   group by player_tag, target_hero_name) x"
            + "   group by x.target_hero_name, x.equipments"
            + "   order by x.target_hero_name, count desc"
            + " ) m"
    )
    List<RankingHeroEquipment> selectRankingHeroEquipments(List<String> playerTags);

    @Query("select player.name as name, player.playerTag as tag, player.trophies as score from PlayerEntity player order by player.trophies desc")
    List<RankingHallOfFame> selectRankingTrophiesCurrent(Pageable pageable);

    @Query("select player.name as name, player.playerTag as tag, player.attackWins as score from PlayerEntity player order by player.attackWins desc")
    List<RankingHallOfFame> selectRankingAttackWins(PageRequest pageable);
}
