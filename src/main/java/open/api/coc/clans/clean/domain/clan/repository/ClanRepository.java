package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.Clan;

public interface ClanRepository {

    boolean exists(String tag);
    Optional<Clan> findById(String tag);
    List<Clan> findByIds(List<String> clanTags);

    List<Clan> findAllClans();
    List<Clan> findAllCapitalClans();
    List<Clan> findAllByWarType(String warType);


    Integer selectMaxOrders();

    Clan save(Clan clan);

}
