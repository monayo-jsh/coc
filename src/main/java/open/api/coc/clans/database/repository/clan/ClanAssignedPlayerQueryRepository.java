package open.api.coc.clans.database.repository.clan;

import java.util.List;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerEntity;

public interface ClanAssignedPlayerQueryRepository {

    String findLatestSeasonDate();

    List<ClanAssignedPlayerEntity> findAllBySeasonDate(String seasonDate);

}
