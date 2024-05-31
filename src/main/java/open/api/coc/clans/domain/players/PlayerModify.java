package open.api.coc.clans.domain.players;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.throwBadRequestException;

import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.util.ObjectUtils;

@Getter
public class PlayerModify {

    private final String playerTag;
    private final YnType supportYn;

    @Builder
    private PlayerModify(String playerTag, YnType supportYn) {
        this.playerTag = playerTag;
        this.supportYn = supportYn;
    }

    public static PlayerModify create(String playerTag, PlayerModifyRequest request) {
        PlayerModify playerModify = PlayerModify.builder()
                                                .playerTag(playerTag)
                                                .supportYn(YnType.valueOf(request.getSupportYn()))
                                                .build();

        playerModify.validate();
        return playerModify;
    }

    public void validate() {
        if (ObjectUtils.isEmpty(supportYn)) {
            throwBadRequestException(ExceptionCode.INVALID_PARAMETER, "지원계정 여부 미입력");
        }
    }
}
