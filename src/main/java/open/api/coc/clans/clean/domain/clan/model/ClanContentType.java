package open.api.coc.clans.clean.domain.clan.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ClanContentType {

    CLAN_WAR("none"),
    CLAN_WAR_LEAGUE("league"),
    CLAN_WAR_PARALLEL("parallel"),
    CLAN_CAPITAL("capital"),
    CLAN_COMPETITION("competition");

    private final String type;

    ClanContentType(String type) {
        this.type = type;
    }

    public static ClanContentType fromType(String type) {
        return Arrays.stream(values())
                     .filter(clanContentType -> clanContentType.type.equalsIgnoreCase(type))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(
                         "Invalid type: " + type + ". Valid values are: " + validTypes()
                     ));
    }

    private static String validTypes() {
        return Arrays.stream(values())
                     .map(clanContentType -> clanContentType.type)
                     .collect(Collectors.joining(", "));
    }

    public boolean isLeagueWar() {
        return CLAN_WAR_LEAGUE.equals(this);
    }
}