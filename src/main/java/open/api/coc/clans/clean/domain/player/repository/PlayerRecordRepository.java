package open.api.coc.clans.clean.domain.player.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;

public interface PlayerRecordRepository {

    List<PlayerRecordHistory> findAll(String playerTag, LocalDateTime fromRecordedAt, LocalDateTime toRecordedAt);

    boolean existsByTag(String playerTag);

    void saveHistory(PlayerRecordHistory recordHistory);

    void deleteById(String playerTag);
}
