package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompetitionClanScheduleRepository extends JpaRepository<CompetitionClanScheduleEntity, Long> {

    boolean existsByIdAndCompetitionClanId(Long id, Long compClanId);
    List<CompetitionClanScheduleEntity> findAllByCompetitionClan_Id(Long compClanId);

}
