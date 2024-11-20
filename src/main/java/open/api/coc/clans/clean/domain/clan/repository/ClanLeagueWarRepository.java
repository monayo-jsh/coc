package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;

public interface ClanLeagueWarRepository {

    List<ClanLeagueWarEntity> findAllBySeason(String season);

}
