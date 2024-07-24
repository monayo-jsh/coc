package open.api.coc.clans.database.repository.clan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;

public interface ClanWarQueryRepository {

    Optional<ClanWarEntity> findByWarId(Long warId);
    List<ClanWarEntity> findAllByStartTimePeriod(LocalDateTime fromStartTime, LocalDateTime toStartTime);

}
