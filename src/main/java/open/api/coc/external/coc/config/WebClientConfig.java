package open.api.coc.external.coc.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final ClashOfClanConfig clashOfClanConfig;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                        .baseUrl(clashOfClanConfig.getDomain())
                        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + clashOfClanConfig.getApiKey())
                        .clientConnector(new ReactorClientHttpConnector(httpClient()))
                        .build();
    }

    private HttpClient httpClient() {
        return HttpClient.create(connectionProvider())
                         .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) clashOfClanConfig.getConnectTimeout().toMillis())
                         .responseTimeout(clashOfClanConfig.getResponseTimeout())
                         .doOnConnected(conn ->
                                            conn.addHandlerLast(new ReadTimeoutHandler((int) clashOfClanConfig.getReadTimeout().toSeconds()))
                                                .addHandlerLast(new WriteTimeoutHandler((int) clashOfClanConfig.getWriteTimeout().toSeconds()))
                         );
    }

    private ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("coc-pool")
                                 .maxConnections(20)              // 1코어 기준, 과도하면 역효과
                                 .pendingAcquireMaxCount(50)      // 대기 큐 제한
                                 .pendingAcquireTimeout(Duration.ofSeconds(5))
                                 .build();
    }

}
