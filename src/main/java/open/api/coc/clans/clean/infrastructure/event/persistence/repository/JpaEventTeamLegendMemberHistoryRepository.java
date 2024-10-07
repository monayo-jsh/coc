package open.api.coc.clans.clean.infrastructure.event.persistence.repository;

import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEventTeamLegendMemberHistoryRepository extends JpaRepository<EventTeamLegendMemberHistoryEntity, Long> {
}
