package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

@Getter
@AllArgsConstructor
public class ClanAssignedPlayerBulkRequest {

    @JsonProperty("season_date")
    private final String seasonDate;

    @JsonProperty("players")
    private final List<ClanAssignedPlayerRequest> players;

    public List<ClanAssignedPlayer> toClanAssignedPlayers() {
        if (CollectionUtils.isEmpty(players)) return Collections.emptyList();
        return players.stream()
                      .map(player -> new ClanAssignedPlayer(player.getPlayerTag(), player.getClanTag()))
                      .collect(Collectors.toList());
    }
}
