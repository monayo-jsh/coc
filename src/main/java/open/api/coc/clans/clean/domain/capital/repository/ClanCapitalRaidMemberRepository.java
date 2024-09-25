package open.api.coc.clans.clean.domain.capital.repository;

import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;

public interface ClanCapitalRaidMemberRepository {

    List<RaiderEntity> findAllByRaidId(Long raidId);

}
