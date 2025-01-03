package open.api.coc.clans.common.exception;

import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import org.springframework.util.StringUtils;

@Getter
public class BadRequestException extends RuntimeException {

    private final String code = ExceptionCode.INVALID_REQUEST.getCode();
    private String message;

    protected BadRequestException(ExceptionCode exceptionCode) {
        this.message = exceptionCode.getMessage();
    }

    public BadRequestException(String code, String message) {
        this.message = message;
    }

    public BadRequestException(String message) {
        this.message = message;
    }

    public BadRequestException addExtraMessage(String message) {
        if (StringUtils.hasText(message)) {
            this.message += " - ";
        }
        this.message += message;

        return this;
    }

    public static BadRequestException create(String code, String message) {
        return new BadRequestException(code, message);
    }

    public static BadRequestException create(ExceptionCode exceptionCode) {
        return create(exceptionCode.getCode(), exceptionCode.getMessage());
    }

    public static BadRequestException create(ExceptionCode exceptionCode, String message) {
        BadRequestException badRequestException = create(exceptionCode);
        badRequestException.addExtraMessage(message);
        return badRequestException;
    }


}
