package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.player.model.dto.PlayerLegendRecordTargetDTO;

public interface PlayerRecordRepository {

    List<PlayerLegendRecordTargetDTO> findAllByName(String playerName);
    boolean existsByTag(String playerTag);

    void save(String playerTag);
    void deleteById(String playerTag);

}
