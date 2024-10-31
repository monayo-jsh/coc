package open.api.coc.clans.clean.domain.player.repository;

import java.util.List;

public interface PlayerRecordRepository {

    List<String> findAllByName(String playerName);
    boolean existsByTag(String playerTag);

    void save(String playerTag);
    void deleteById(String playerTag);

}
