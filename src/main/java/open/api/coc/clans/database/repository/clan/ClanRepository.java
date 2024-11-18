package open.api.coc.clans.database.repository.clan;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<ClanEntity, String> {

    @Query("select max(c.order) from ClanEntity c")
    Integer selectMaxOrders();

}
