package open.api.coc.clans.clean.domain.player.repository;

import open.api.coc.clans.clean.domain.player.model.PlayerRecordHistory;

public interface PlayerRecordRepository {

    boolean existsByTag(String playerTag);

    void saveHistory(PlayerRecordHistory recordHistory);

}
