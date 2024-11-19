package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import open.api.coc.clans.database.entity.clan.ClanWarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClanWarRepository extends JpaRepository<ClanWarEntity, Long> {
}
