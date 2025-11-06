package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordEntity;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordPK;

public interface PlayerRecordRepository {

    List<PlayerLegendRecordTargetDTO> findAllByNameOrNickname(String playerName);
    boolean existsByTag(PlayerRecordPK id);

    Optional<PlayerRecordEntity> findById(PlayerRecordPK id);

    void save(PlayerRecordEntity playerRecordEntity);

    void deleteById(PlayerRecordPK id);

}
