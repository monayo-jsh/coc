package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import open.api.coc.clans.domain.players.PlayerResponse;

@Setter
@Getter
public class ClanAssignedMemberListResponse {

    private String clanTag;
    private String seasonDate;
    private final List<PlayerResponse> players;

    private ClanAssignedMemberListResponse(String clanTag, String seasonDate, List<PlayerResponse> players) {
        this.clanTag = clanTag;
        this.seasonDate = seasonDate;
        this.players = players;
    }

    public static ClanAssignedMemberListResponse create(String clanTag, String latestSeasonDate, List<PlayerResponse> players) {
        return new ClanAssignedMemberListResponse(clanTag, latestSeasonDate, players);
    }
}
