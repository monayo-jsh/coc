package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;

public interface PlayerRecordRepository {

    List<PlayerLegendRecordTargetDTO> findAllByNameOrNickname(String playerName);
    boolean existsByTag(String playerTag);

    Optional<PlayerRecordEntity> findById(String playerTag);

    void save(PlayerRecordEntity playerRecordEntity);

    void deleteById(String playerTag);

}
