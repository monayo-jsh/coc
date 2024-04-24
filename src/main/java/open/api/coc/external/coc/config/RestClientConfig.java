package open.api.coc.external.coc.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class RestClientConfig {

    private final ClashOfClanConfig clashOfClanConfig;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(clashOfClanConfig.getDomain())
                .defaultHeader("Authorization", "Bearer " + clashOfClanConfig.getApiKey())
                .build();
    }
}