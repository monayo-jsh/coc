package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordHistoryEntity;
import open.api.coc.clans.domain.players.PlayerRecordResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlayerRecordHistoryRepository extends JpaRepository<PlayerRecordHistoryEntity, String> {

    @Query(
        value = "select new open.api.coc.clans.domain.players.PlayerRecordResponse("
              + "  pr.tag, p.name, pr.oldTrophies, pr.newTrophies, pr.oldAttackWins, pr.newAttackWins, pr.oldDefenceWins, pr.newDefenceWins, pr.recordedAt"
              + " ) "
              + "from PlayerRecordHistoryEntity pr "
              + "join PlayerEntity p on p.playerTag = pr.tag "
              + "where pr.tag = :playerTag "
              + "and pr.recordedAt >= :startDateTime "
              + "and pr.recordedAt < :endDateTime "
              + "order by pr.recordedAt desc "
    )
    List<PlayerRecordResponse> findAllById(String playerTag, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
