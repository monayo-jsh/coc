package open.api.coc.clans.common.exception;

import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import org.springframework.util.StringUtils;

@Getter
public class BadRequestException extends RuntimeException {

    private final String code;
    private String message;

    public BadRequestException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addExtraMessage(String message) {
        if (StringUtils.hasText(message)) {
            this.message += " - ";
        }
        this.message += message;
    }

    public static BadRequestException create(ExceptionCode exceptionCode, String message) {
        BadRequestException badRequestException = new BadRequestException(exceptionCode.getCode(), exceptionCode.getMessage());
        badRequestException.addExtraMessage(message);
        return badRequestException;
    }

    public static BadRequestException create(String code, String message) {
        return new BadRequestException(code, message);
    }

}
