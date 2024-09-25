package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import java.util.Optional;
import open.api.coc.clans.database.entity.clan.ClanEntity;

public interface ClanRepository {

    boolean exists(String tag);
    Optional<ClanEntity> findById(String tag);
    List<ClanEntity> findByIds(List<String> clanTags);

}
