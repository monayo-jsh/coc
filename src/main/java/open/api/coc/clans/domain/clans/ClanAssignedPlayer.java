package open.api.coc.clans.domain.clans;

import lombok.Getter;

@Getter
public class ClanAssignedPlayer {

    private final String playerTag;
    private final String clanTag;

    public ClanAssignedPlayer(String playerTag, String clanTag) {
        this.playerTag = playerTag;
        this.clanTag = clanTag;
    }
}
