package open.api.coc.clans.domain.players;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PlayerModifyRequest {

    @JsonProperty("support_yn")
    private final String supportYn;

    @JsonCreator
    public PlayerModifyRequest(String supportYn) {
        this.supportYn = supportYn;
    }
}
