package open.api.coc.clans.clean.infrastructure.event.persistence.repository;

import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEventRepository extends JpaRepository<EventEntity, Long> {
}
