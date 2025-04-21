package open.api.coc.external.coc.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.io.IOException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final ClashOfClanConfig clashOfClanConfig;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                         .requestFactory(restClientHttpRequestFactory())
                         .baseUrl(clashOfClanConfig.getDomain())
                         .defaultHeader("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                         .build();
    }

    private ClientHttpRequestFactory restClientHttpRequestFactory() {
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS.withConnectTimeout(Duration.ofSeconds(3))
                                                                                             .withReadTimeout(Duration.ofSeconds(10));
        return ClientHttpRequestFactories.get(settings);
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                                                          .failureRateThreshold(50) // 실패율 50% 이상이면 OPEN
                                                          .slidingWindowType(SlidingWindowType.COUNT_BASED)
                                                          .slidingWindowSize(10)
                                                          .minimumNumberOfCalls(5)
                                                          .permittedNumberOfCallsInHalfOpenState(3)
                                                          .waitDurationInOpenState(Duration.ofSeconds(10))
                                                          .recordExceptions(IOException.class, CustomRuntimeException.class)
                                                          .ignoreExceptions(IllegalArgumentException.class)
                                                          .build();

        return CircuitBreakerRegistry.of(config);
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker("clashOfClanApi");
    }
}
