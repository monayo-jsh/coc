package open.api.coc.external.coc.clan;

import java.util.Map;
import java.util.Optional;
import open.api.coc.external.coc.clan.domain.ClanCapitalRaidSeasons;
import org.springframework.stereotype.Component;

@Component
public interface ClanApiService {

    Map<String, Object> findClanByClanTag(String clanTag);

    Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit);

}
