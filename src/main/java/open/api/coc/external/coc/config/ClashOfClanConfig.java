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
        private final Players players;
        private final Leagues leagues;
        private final ClanWarLeagues clanWarLeagues;

        @RequiredArgsConstructor
        private static class Clans {
            private final String prefix;
            private final String clanTag;
            private final String capitalRaidSeasons;
            private final String currentWar;
            private final String leagueGroup;
            private final String members;

            public String getClanTag() {
                return prefix + clanTag;
            }
            public String getCapitalRaidSeasons() {
                return prefix + capitalRaidSeasons;
            }
            public String getCurrentWar() {
                return prefix + currentWar;
            }
            public String getLeagueGroup() { return prefix + leagueGroup; }
            public String getMembers() { return prefix + members; }
        }

        @RequiredArgsConstructor
        private static class Players {
            private final String prefix;
            private final String player;

            public String getPlayer() {
                return prefix + player;
            }
        }

        @RequiredArgsConstructor
        private static class Leagues {
            private final String prefix;
            private final String leagues;

            public String getLeagues() {
                return prefix + leagues;
            }
        }

        @RequiredArgsConstructor
        private static class ClanWarLeagues {
            private final String prefix;
            private final String roundTag;
            public String getClanWarLeagues() { return prefix + roundTag; }
        }
    }

    public String getClansClanTagUri() {
        return getEndPoint().getClans().getClanTag();
    }

    public String getClansClanTagCapitalRaidSeasonsUri() {
        return getEndPoint().getClans().getCapitalRaidSeasons();
    }

    public String getClansClanTagCurrentWarUri() {
        return getEndPoint().getClans().getCurrentWar();
    }
    public String getClansClanTagCurrentLeagueGroupUri() { return getEndPoint().getClans().getLeagueGroup(); }
    public String getClansClanMembersUri() {
        return getEndPoint().getClans().getMembers();
    }

    public String getPlayerUri() {
        return getEndPoint().getPlayers().getPlayer();
    }

    public String getLeaguesUri() {
        return getEndPoint().getLeagues().getLeagues();
    }

    public String getClanWarLeagueUri() { return getEndPoint().getClanWarLeagues().getClanWarLeagues();}

}
