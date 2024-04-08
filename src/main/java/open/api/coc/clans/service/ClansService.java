package open.api.coc.clans.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.ClashOfClansApi;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClansService {

    private final ClashOfClansApi clashOfClansApi;


    public Map<String, Object> findClanByClanTag(String clanTag) {
        return clashOfClansApi.findClanByClanTag(clanTag);
    }

    public Map<String, Object> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit) {
        Map<String, Object> clanCapitalRaidSeasons = clashOfClansApi.findClanCapitalRaidSeasonsByClanTagAndLimit(clanTag, limit);
        if (ObjectUtils.isEmpty(clanCapitalRaidSeasons.get("items"))) {
            log.info("not found items.. {}", clanTag);
            return Collections.emptyMap();
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) clanCapitalRaidSeasons.get("items");
        Optional<Map<String, Object>> first = items.stream().findFirst();

        if (first.isEmpty()) {
            log.info("not found items.. {}", clanTag);
            return Collections.emptyMap();
        }

        Map<String, Object> itemMap = first.get();

        if (ObjectUtils.isEmpty(itemMap.get("members"))) {
            log.info("not found members.. {}", itemMap);
            return Collections.emptyMap();
        }

        List<Map<String, Object>> members = (List<Map<String, Object>>) itemMap.get("members");
        return new HashMap<>(){
            {
                put(clanTag, members.size());
            }
        };
    }
}
