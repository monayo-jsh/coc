package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import open.api.coc.clans.database.entity.clan.ClanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClanRepository extends JpaRepository<ClanEntity, String> {

    boolean existsById(String tag);

}
