package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
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
    public List<PlayerRecordHistory> findAll(String playerTag, LocalDateTime fromRecordedAt, LocalDateTime toRecordedAt) {
        return jpaPlayerRecordHistoryRepository.findAllByIdAndRecordedAtBetween(playerTag, fromRecordedAt, toRecordedAt)
                                               .stream()
                                               .map(recordHistoryEntityMapper::toPlayerRecordHistory)
                                               .toList();
    }

    @Override
    public boolean existsByTag(String playerTag) {
        return jpaPlayerRecordRepository.existsById(playerTag);
    }

    @Override
    public void saveHistory(PlayerRecordHistory recordHistory) {
        PlayerRecordHistoryEntity entity = recordHistoryEntityMapper.toPlayerRecordHistoryEntity(recordHistory);
        PlayerEntity playerEntity = PlayerEntity.builder().playerTag(recordHistory.getTag()).build();
        entity.changePlayer(playerEntity);

        jpaPlayerRecordHistoryRepository.save(entity);
    }

    @Override
    public void deleteById(String playerTag) {
        jpaPlayerRecordRepository.deleteById(playerTag);
    }
}
