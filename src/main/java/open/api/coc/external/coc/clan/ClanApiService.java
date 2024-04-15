package open.api.coc.external.coc.clan;

import java.util.Map;
import java.util.Optional;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import org.springframework.stereotype.Component;

@Component
public interface ClanApiService {

    Map<String, Object> findClanByClanTag(String clanTag);

    Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit);

    Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag);
}