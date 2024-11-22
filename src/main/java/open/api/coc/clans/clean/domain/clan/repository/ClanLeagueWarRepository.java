package open.api.coc.clans.clean.domain.clan.repository;

import java.util.List;
import java.util.Set;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;

public interface ClanLeagueWarRepository {

    List<ClanLeagueWarEntity> findAllBySeasonAndClanTagIn(String season, Set<String> clanTags);

}
