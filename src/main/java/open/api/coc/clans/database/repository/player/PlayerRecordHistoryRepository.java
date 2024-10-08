package open.api.coc.clans.database.repository.player;

import java.util.List;
import open.api.coc.clans.database.entity.player.PlayerRecordHistoryEntity;
import open.api.coc.clans.domain.players.PlayerRecordResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRecordHistoryRepository extends JpaRepository<PlayerRecordHistoryEntity, String> {


    @Query(
        value = "select new open.api.coc.clans.domain.players.PlayerRecordResponse(pr.tag, p.name, pr.oldTrophies, pr.newTrophies) "
              + "from PlayerRecordHistoryEntity pr "
              + "join PlayerEntity p on p.playerTag = pr.tag "
              + "where pr.tag = :playerTag"
    )
    List<PlayerRecordResponse> findAllById(String playerTag, Pageable pageable);

}
