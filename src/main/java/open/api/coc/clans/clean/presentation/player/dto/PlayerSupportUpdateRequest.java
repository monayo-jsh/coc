package open.api.coc.clans.clean.presentation.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PlayerSupportUpdateRequest {

    @NotNull
    @NotEmpty
    @Pattern(regexp = "[YN]")
    @JsonProperty("support_yn")
    private final String supportYn;

    @JsonCreator
    public PlayerSupportUpdateRequest(String supportYn) {
        this.supportYn = supportYn;
    }

}
