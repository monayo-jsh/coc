package open.api.coc.clans.clean.infrastructure.player.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerRecordHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPlayerRecordHistoryRepository extends JpaRepository<PlayerRecordHistoryEntity, String> {

    @Query(
        value = "select pr "
            + "from PlayerRecordHistoryEntity pr "
            + "join fetch pr.player "
            + "where pr.player.playerTag = :playerTag "
            + "and pr.recordedAt >= :startDateTime "
            + "and pr.recordedAt < :endDateTime "
            + "order by pr.recordedAt desc "
    )
    List<PlayerRecordHistoryEntity> findAllByIdAndRecordedAtBetween(String playerTag, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
