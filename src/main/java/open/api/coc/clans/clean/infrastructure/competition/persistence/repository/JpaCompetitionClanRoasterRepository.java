package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterEntity;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCompetitionClanRoasterRepository extends JpaRepository<CompetitionClanRoasterEntity, CompetitionClanRoasterPK> {

    List<CompetitionClanRoasterEntity> findAllByIdCompClanId(Long compClanId);

}
