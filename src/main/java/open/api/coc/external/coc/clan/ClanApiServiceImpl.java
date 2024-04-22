package open.api.coc.external.coc.clan;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.external.coc.clan.domain.capital.ClanCapitalRaidSeasons;
import open.api.coc.external.coc.clan.domain.clan.ClanMemberList;
import open.api.coc.external.coc.clan.domain.clan.ClanWar;
import open.api.coc.external.coc.clan.domain.player.Player;
import open.api.coc.external.coc.config.ClashOfClanConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClanApiServiceImpl implements open.api.coc.external.coc.clan.ClanApiService {

    private final ClashOfClanConfig clashOfClanConfig;

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> findClanByClanTag(String clanTag) {
        return RestClient.create()
                         .get()
                         .uri(clashOfClanConfig.getClansClanTagUri(), clanTag)
                         .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                         .retrieve()
                         .body(Map.class);
    }

    @Override
    public Optional<ClanCapitalRaidSeasons> findClanCapitalRaidSeasonsByClanTagAndLimit(String clanTag, int limit) {
        String uri = clashOfClanConfig.getClansClanTagCapitalRaidSeasonsUri() + "?limit=" + limit;
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(uri, clanTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(ClanCapitalRaidSeasons.class));
    }

    @Override
    public Optional<ClanWar> findClanCurrentWarByClanTag(String clanTag) {
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(clashOfClanConfig.getClansClanTagCurrentWarUri(), clanTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(ClanWar.class));
    }

    @Override
    public Optional<ClanMemberList> findClanMembersByClanTag(String clanTag) {
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(clashOfClanConfig.getClansClanMembersUri(), clanTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(ClanMemberList.class));
    }

    @Override
    @Cacheable(cacheManager = "playerCacheManager", value="players", key = "#playTag")
    public Optional<Player> findPlayerBy(String playTag) {
        // 캐시된 데이터 응답 & 캐시 데이터 없을 경우 실연동
        return findPlayer(playTag);
    }

    @Override
    @CachePut(cacheManager = "playerCacheManager", value="players", key = "#playTag")
    public Optional<Player> fetchPlayerBy(String playTag) {
        // 사용자 정보 캐싱 등록 & 스케줄러 동작
        return findPlayer(playTag);
    }

    private Optional<Player> findPlayer(String playTag) {
        return Optional.ofNullable(RestClient.create()
                                             .get()
                                             .uri(clashOfClanConfig.getPlayerUri(), playTag)
                                             .header("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                                             .retrieve()
                                             .body(Player.class));
    }
}
