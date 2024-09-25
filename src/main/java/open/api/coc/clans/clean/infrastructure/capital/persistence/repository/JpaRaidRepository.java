package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRaidRepository extends JpaRepository<RaidEntity, Long> {
}
