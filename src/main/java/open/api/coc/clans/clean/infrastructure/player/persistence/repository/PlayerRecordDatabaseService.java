package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordPK;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRecordDatabaseService implements PlayerRecordRepository {

    private final JpaPlayerRecordRepository jpaPlayerRecordRepository;


    @Override
    public List<PlayerLegendRecordTargetDTO> findAllByNameOrNickname(String playerName) {
        return jpaPlayerRecordRepository.findAllByNameOrNickname(playerName);
    }

    @Override
    public boolean existsByTag(PlayerRecordPK id) {
        return jpaPlayerRecordRepository.existsById(id);
    }

    @Override
    public Optional<PlayerRecordEntity> findById(PlayerRecordPK id) {
        return jpaPlayerRecordRepository.findById(id);
    }


    @Override
    public void save(PlayerRecordEntity playerRecordEntity) {
        jpaPlayerRecordRepository.save(playerRecordEntity);
    }

    @Override
    public void deleteById(PlayerRecordPK id) {
        jpaPlayerRecordRepository.deleteById(id);
    }

}

