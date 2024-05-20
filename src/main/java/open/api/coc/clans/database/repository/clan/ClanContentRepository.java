package open.api.coc.clans.database.repository.clan;

import open.api.coc.clans.database.entity.clan.ClanContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanContentRepository extends JpaRepository<ClanContentEntity, String> {

}
