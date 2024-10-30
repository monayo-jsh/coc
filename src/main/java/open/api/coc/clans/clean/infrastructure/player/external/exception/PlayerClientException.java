package open.api.coc.clans.clean.infrastructure.player.external.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;

public class PlayerClientException extends CustomRuntimeException {

    public PlayerClientException(String playerTag) {
        super(ExceptionCode.EXTERNAL_ERROR.getCode(), "플레이어 조회 실패: %s".formatted(playerTag));
    }

}
