package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanGameMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClanGameMetaRepository extends JpaRepository<ClanGameMetaEntity, String> {
}
