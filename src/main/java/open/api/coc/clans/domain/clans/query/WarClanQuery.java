package open.api.coc.clans.domain.clans.query;

import lombok.Getter;

@Getter
public class WarClanQuery {

    private final ClanWarType type;

    private WarClanQuery(ClanWarType type) {
        this.type = type;
    }

    public static WarClanQuery create(String type) {
        ClanWarType clanWarType = ClanWarType.valueOf(type.toLowerCase());
        return new WarClanQuery(clanWarType);
    }

}
