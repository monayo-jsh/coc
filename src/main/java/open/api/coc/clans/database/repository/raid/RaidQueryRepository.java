package open.api.coc.clans.database.repository.raid;

import java.time.LocalDate;
import java.util.List;
import open.api.coc.clans.database.entity.raid.RaidEntity;

public interface RaidQueryRepository {

    List<RaidEntity> findAllByStartDate(LocalDate startDate);

}
