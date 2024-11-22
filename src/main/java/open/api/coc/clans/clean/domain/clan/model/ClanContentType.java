package open.api.coc.clans.clean.domain.clan.model;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum ClanContentType {
    CLAN_WAR("none"),
    CLAN_WAR_LEAGUE("league"),
    CLAN_WAR_PARALLEL("parallel"),
    CLAN_CAPITAL("capital"),
    CLAN_COMPETITION("competition");

    private final String warType;

    ClanContentType(String warType) {
        this.warType = warType;
    }

    public static ClanContentType fromWarType(String warType) {
        return Arrays.stream(values())
                     .filter(clanContentType -> clanContentType.warType.equalsIgnoreCase(warType))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException(
                         "Invalid warType: " + warType + ". Valid values are: " + validWarTypes()
                     ));
    }

    private static String validWarTypes() {
        return Arrays.stream(values())
                     .map(clanContentType -> clanContentType.warType)
                     .collect(Collectors.joining(", "));
    }

    public boolean isLeagueWar() {
        return CLAN_WAR_LEAGUE.equals(this);
    }
}