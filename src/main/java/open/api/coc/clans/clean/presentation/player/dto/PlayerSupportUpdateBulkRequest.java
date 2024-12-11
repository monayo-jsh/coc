package open.api.coc.clans.clean.presentation.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

@Getter
public class PlayerSupportUpdateBulkRequest {

    @NotEmpty
    @JsonProperty("player_tags")
    private final List<String> playerTags;

    @JsonCreator
    public PlayerSupportUpdateBulkRequest(List<String> playerTags) {
        this.playerTags = playerTags;
    }

}
