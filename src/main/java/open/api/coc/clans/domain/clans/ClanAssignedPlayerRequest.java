package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClanAssignedPlayerRequest {

    @JsonProperty("player_tag")
    private final String playerTag;

    @JsonProperty("clan_tag")
    private final String clanTag;

}
