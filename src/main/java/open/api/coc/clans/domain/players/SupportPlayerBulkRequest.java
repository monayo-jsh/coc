package open.api.coc.clans.domain.players;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;

@Getter
public class SupportPlayerBulkRequest {

    @NotEmpty
    @JsonProperty("player_tags")
    private final List<String> playerTags;

    @JsonCreator
    public SupportPlayerBulkRequest(List<String> playerTags) {
        this.playerTags = playerTags;
    }
}
