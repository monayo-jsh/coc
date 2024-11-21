package open.api.coc.clans.clean.domain.clan.model;

public enum ClanContentType {
    CLAN_WAR,
    CLAN_WAR_LEAGUE,
    CLAN_WAR_PARALLEL,
    CLAN_CAPITAL,
    CLAN_COMPETITION;

    public static ClanContentType ofWartype(String warType) {
        switch (warType.toLowerCase()) {
            case "none" -> { return CLAN_WAR; }
            case "league" -> { return CLAN_WAR_LEAGUE; }
            case "parallel" -> { return CLAN_WAR_PARALLEL; }
        }

        throw new IllegalArgumentException("warType is none|league|parallel");
    }
}