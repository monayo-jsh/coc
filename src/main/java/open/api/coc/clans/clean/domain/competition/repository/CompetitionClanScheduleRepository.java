package open.api.coc.clans.clean.domain.competition.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;

public interface CompetitionClanScheduleRepository {

    void save(CompetitionClanScheduleEntity entity);
    void deleteById(Long clanScheduleId);

    boolean existsByIdAndCompClanId(Long id, Long compClanId);
    boolean notExistsByIdAndCompClanId(Long id, Long compClanId);

    List<CompetitionClanScheduleEntity> findAllByCompClanId(Long compClanId);
    Optional<CompetitionClanScheduleEntity> findById(Long id);
}
