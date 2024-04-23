package open.api.coc.clans.common.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    @Bean
    public CacheManager playerCacheManager() {
        ConcurrentMapCacheManager players = new ConcurrentMapCacheManager("players");
        players.setAllowNullValues(false);
        return players;
    }

}
