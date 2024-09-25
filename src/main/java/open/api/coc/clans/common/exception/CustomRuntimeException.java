package open.api.coc.clans.common.exception;

import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import org.springframework.util.StringUtils;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private final String code;
    private String message;

    public CustomRuntimeException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public CustomRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addExtraMessage(String message) {
        if (StringUtils.hasText(message)) {
            this.message += " - ";
        }
        this.message += message;
    }

    public static CustomRuntimeException create(ExceptionCode exceptionCode, String message) {
        CustomRuntimeException customRuntimeException = new CustomRuntimeException(exceptionCode);
        customRuntimeException.addExtraMessage(message);
        return customRuntimeException;
    }
}
