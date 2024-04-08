package open.api.coc.external.coc;

import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public interface ClashOfClansApi {

    Map<String, Object> findClanByClanTag(String clanTag);

    Map<String, Object> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit);

}
