package open.api.coc.clans.database.repository.clan;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<ClanEntity, String> {

    @Override
    @Query("select c from ClanEntity c join fetch c.clanContent where c.visibleYn = 'Y' order by c.order")
    List<ClanEntity> findAll();

    @Query("select max(c.order) from ClanEntity c")
    Integer selectMaxOrders();

    @Query("select c from ClanEntity c join fetch c.clanContent where c.visibleYn = 'Y' and c.clanContent.clanWarYn = 'Y' order by c.order")
    List<ClanEntity> findClanWarList();

    @Query("select c from ClanEntity c join fetch c.clanContent where c.visibleYn = 'Y' and c.clanContent.clanCapitalYn = 'Y' order by c.order")
    List<ClanEntity> findClanCapitalList();

    @Query("select c from ClanEntity c join fetch c.clanContent where c.visibleYn = 'Y' and c.clanContent.warLeagueYn = 'Y' order by c.order")
    List<ClanEntity> findClanLeagueList();

    @Query("select c from ClanEntity c join fetch c.clanContent where c.visibleYn = 'Y' and c.clanContent.clanWarParallelYn = 'Y' order by c.order")
    List<ClanEntity> findClanWarParallelList();

}
