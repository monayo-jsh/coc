package open.api.coc.clans.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.ClashOfClansApi;
import open.api.coc.external.coc.domain.ClanAttackerRes;
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

    public ClanAttackerRes findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit) {
        Map<String, Object> clanCapitalRaidSeasons = clashOfClansApi.findClanCapitalRaidSeasonsByClanTagAndLimit(clanTag, limit);
        if (ObjectUtils.isEmpty(clanCapitalRaidSeasons.get("items"))) {
            log.info("not found items.. {}", clanTag);
            return ClanAttackerRes.empty(clanTag);
        }

        List<Map<String, Object>> items = (List<Map<String, Object>>) clanCapitalRaidSeasons.get("items");
        Optional<Map<String, Object>> first = items.stream().findFirst();

        if (first.isEmpty()) {
            log.info("not found items.. {}", clanTag);
            return ClanAttackerRes.empty(clanTag);
        }

        Map<String, Object> itemMap = first.get();

        if (ObjectUtils.isEmpty(itemMap.get("members"))) {
            log.info("not found members.. {}", itemMap);
            return ClanAttackerRes.empty(clanTag);
        }

        List<Map<String, Object>> members = (List<Map<String, Object>>) itemMap.get("members");
        return ClanAttackerRes.create(clanTag, members.size());
    }

    public List<ClanAttackerRes> getCapitalAttackers() throws ExecutionException, InterruptedException {
        List<String> CLAN_TAGS = ClanAttackerRes.CLAN_TAGS.keySet().stream().toList();

        ForkJoinPool forkJoinPool = new ForkJoinPool(CLAN_TAGS.size());
        List<ClanAttackerRes> clanAttackers = forkJoinPool.submit(() -> CLAN_TAGS.stream()
                                                                                 .parallel()
                                                                                 .map(clanTag -> findClanCapitalRaidSeasonsByClanTagAndLimit(clanTag, 1))
                                                                                 .collect(Collectors.toList()))
                                                          .get();

        return clanAttackers.stream()
                            .sorted(Comparator.comparing(ClanAttackerRes::getName))
                            .collect(Collectors.toList());
    }
}
