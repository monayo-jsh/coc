package open.api.coc.external.coc.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "external.coc")
public class ClashOfClanConfig {
    private final String apiKey;
    private final String domain;

    private final EndPoint endPoint;
    @Getter
    @RequiredArgsConstructor
    private static class EndPoint {
        private final String clansClanTag;
    }

    public String getClansClanTagUri() {
        return getDomain() + getEndPoint().getClansClanTag();
    }
}
