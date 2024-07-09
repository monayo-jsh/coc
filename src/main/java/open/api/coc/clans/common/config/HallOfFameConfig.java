package open.api.coc.clans.common.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "hall-of-fame")
public class HallOfFameConfig {
    private final Integer ranking;
    private final Integer average;
}
