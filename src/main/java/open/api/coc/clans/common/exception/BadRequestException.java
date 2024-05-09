package open.api.coc.clans.common.exception;

import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import org.springframework.util.StringUtils;

@Getter
public class BadRequestException extends RuntimeException {

    private final String code;
    private String message;

    public BadRequestException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public void addExtraMessage(String message) {
        if (StringUtils.hasText(message)) {
            this.message += " - ";
        }
        this.message += message;
    }

    public static BadRequestException create(ExceptionCode exceptionCode, String message) {
        BadRequestException badRequestException = new BadRequestException(exceptionCode);
        badRequestException.addExtraMessage(message);
        return badRequestException;
    }

}
