package open.api.coc.clans.clean.presentation.player.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PlayerNicknameUpdateRequest {

    @NotNull
    @NotEmpty
    private final String nickname;

    @JsonCreator
    public PlayerNicknameUpdateRequest(String nickname) {
        this.nickname = nickname;
    }

}
