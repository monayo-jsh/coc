package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordHistoryEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.mapper.PlayerRecordHistoryEntityMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRecordDatabaseService implements PlayerRecordRepository {

    private final JpaPlayerRecordRepository jpaPlayerRecordRepository;
    private final JpaPlayerRecordHistoryRepository jpaPlayerRecordHistoryRepository;

    private final PlayerRecordHistoryEntityMapper recordHistoryEntityMapper;

    @Override
    public boolean existsByTag(String playerTag) {
        return jpaPlayerRecordRepository.existsById(playerTag);
    }

    @Override
    public void saveHistory(PlayerRecordHistory recordHistory) {
        PlayerRecordHistoryEntity entity = recordHistoryEntityMapper.toPlayerRecordHistoryEntity(recordHistory);
        jpaPlayerRecordHistoryRepository.save(entity);
    }
}
