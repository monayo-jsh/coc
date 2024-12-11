package open.api.coc.clans.common.exception;

import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code = ExceptionCode.ERROR_NOT_FOUND.getCode();
    private final String message;

    public NotFoundException(String message) {
        this.message = message;
    }

}
