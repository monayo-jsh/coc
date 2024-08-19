package open.api.coc.clans.domain.players;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PlayerModifyRequest {

    @NotNull @NotEmpty
    @Pattern(regexp = "[YN]")
    @JsonProperty("support_yn")
    private final String supportYn;

    @JsonCreator
    public PlayerModifyRequest(String supportYn) {
        this.supportYn = supportYn;
    }
}
