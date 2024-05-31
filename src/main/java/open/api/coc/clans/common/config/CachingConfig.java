package open.api.coc.clans.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

//@Configuration
//@EnableCaching
public class CachingConfig {

//    @Bean
    public CacheManager playerCacheManager() {
        ConcurrentMapCacheManager players = new ConcurrentMapCacheManager("players");
        players.setAllowNullValues(false);
        return players;
    }

}
