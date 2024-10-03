package open.api.coc.clans.clean.domain.player.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class PlayerAlreadyExistsException extends BadRequestException {

    public PlayerAlreadyExistsException(String playerTag) {
        super(ExceptionCode.ALREADY_DATA.getCode(), "이미 등록된 플레이어: %s".formatted(playerTag));
    }

}
