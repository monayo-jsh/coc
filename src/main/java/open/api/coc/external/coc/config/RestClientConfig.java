package open.api.coc.external.coc.config;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
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
        ClientHttpRequestFactorySettings settings = ClientHttpRequestFactorySettings.DEFAULTS.withConnectTimeout(Duration.ofSeconds(1));
        return ClientHttpRequestFactories.get(settings);
    }
}
