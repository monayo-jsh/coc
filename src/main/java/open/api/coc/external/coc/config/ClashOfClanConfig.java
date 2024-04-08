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

        private final Clans clans;


        @RequiredArgsConstructor
        private static class Clans {
            private final String prefix;
            private final String clanTag;
            private final String capitalRaidSeasons;

            public String getClanTag() {
                return prefix + clanTag;
            }

            public String getCapitalRaidSeasons() {
                return prefix + capitalRaidSeasons;
            }
        }

    }

    public String getClansClanTagUri() {
        return getDomain() + getEndPoint().getClans().getClanTag();
    }

    public String getClansClanTagCapitalRaidSeasons() {
        return getDomain() + getEndPoint().getClans().getCapitalRaidSeasons();
    }
}
