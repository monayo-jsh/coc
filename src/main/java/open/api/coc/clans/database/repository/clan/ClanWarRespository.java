package open.api.coc.clans.database.repository.clan;

import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanWarRespository extends JpaRepository<ClanWarEntity, Long> {
}
