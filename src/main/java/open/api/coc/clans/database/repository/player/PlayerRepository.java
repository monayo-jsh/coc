package open.api.coc.clans.database.repository.player;

import java.util.List;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.player.PlayerEntity;
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
}
