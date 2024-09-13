package open.api.coc.clans.clean.domain.clan.repository;

import java.util.Optional;
import open.api.coc.clans.database.entity.clan.ClanEntity;

public interface ClanRepository {

    Optional<ClanEntity> findById(String tag);
    boolean exists(String tag);

}
