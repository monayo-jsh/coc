package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.player.repository.PlayerRecordRepository;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRecordDatabaseService implements PlayerRecordRepository {

    private final JpaPlayerRecordRepository jpaPlayerRecordRepository;


    @Override
    public List<String> findAllByName(String playerName) {
        return jpaPlayerRecordRepository.findAllByName(playerName);
    }

    @Override
    public boolean existsByTag(String playerTag) {
        return jpaPlayerRecordRepository.existsById(playerTag);
    }


    @Override
    public void save(String playerTag) {
        PlayerRecordEntity recordEntity = PlayerRecordEntity.builder().tag(playerTag).build();
        jpaPlayerRecordRepository.save(recordEntity);
    }

    @Override
    public void deleteById(String playerTag) {
        jpaPlayerRecordRepository.deleteById(playerTag);
    }

}

