package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRaiderRepository extends JpaRepository<RaiderEntity, Long> {

    List<RaiderEntity> findAllByRaidId(Long raidId);

}
