package open.api.coc.clans.clean.infrastructure.competition.persistence.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.competition.repository.CompetitionClanScheduleRepository;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanScheduleEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CompetitionClanScheduleCoreRepository implements CompetitionClanScheduleRepository {

    private final JpaCompetitionClanScheduleRepository jpaCompetitionClanScheduleRepository;

    @Override
    public void save(CompetitionClanScheduleEntity entity) {
        jpaCompetitionClanScheduleRepository.save(entity);
    }

    @Override
    public void deleteById(Long clanScheduleId) {
        jpaCompetitionClanScheduleRepository.deleteById(clanScheduleId);
    }

    @Override
    public boolean existsByIdAndCompClanId(Long id, Long compClanId) {
        return jpaCompetitionClanScheduleRepository.existsByIdAndCompetitionClanId(id, compClanId);
    }

    @Override
    public boolean notExistsByIdAndCompClanId(Long id, Long compClanId) {
        return !existsByIdAndCompClanId(id, compClanId);
    }

    @Override
    public List<CompetitionClanScheduleEntity> findAllByCompClanId(Long compClanId) {
        if (compClanId == null) {
            throw new IllegalArgumentException("compClanId can not be null");
        }
        return jpaCompetitionClanScheduleRepository.findAllByCompetitionClan_Id(compClanId);
    }

    @Override
    public Optional<CompetitionClanScheduleEntity> findById(Long id) {
        return jpaCompetitionClanScheduleRepository.findById(id);
    }

}
