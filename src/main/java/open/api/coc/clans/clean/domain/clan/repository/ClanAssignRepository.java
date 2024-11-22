package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanAssignedPlayer;

public interface ClanAssignRepository {

    String findLatestAssignedMonth();

    List<ClanAssignedPlayer> findAll(String assignedDate, String clanTag);

    void cancel(String seasonDate, String playerTag);

}
