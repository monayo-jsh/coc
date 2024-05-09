package open.api.coc.clans.common.exception;

import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import org.springframework.util.StringUtils;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;
    private String message;

    public NotFoundException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public void addExtraMessage(String message) {
        if (StringUtils.hasText(message)) {
            this.message += " - ";
        }
        this.message += message;
    }

    public static NotFoundException create(String message) {
        NotFoundException notFoundException = new NotFoundException(ExceptionCode.ERROR_NOT_FOUND);
        notFoundException.addExtraMessage(message);
        return notFoundException;
    }

}
