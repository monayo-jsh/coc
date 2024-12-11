package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.clan.model.ClanContentType;

public interface ClanRepository {

    boolean exists(String tag);
    Optional<Clan> findById(String tag);
    List<Clan> findByIds(List<String> clanTags);

    List<Clan> findAllActiveClans();
    List<Clan> findAllByClanContentType(ClanContentType clanContentType);

    Integer selectMaxOrders();

    Clan save(Clan clan);

}
