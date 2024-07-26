package open.api.coc.clans.common.exception.handler;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException e) {
        String responseBody = formatMessage(e.getCode(), e.getMessage());
        return ResponseEntity.badRequest()
                             .body(responseBody);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER)
                                                                     .addExtraMessage(formatMessage(e.getParameterName(), " must not be null"));

        return badRequestException(badRequestException);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER)
                                                                     .addExtraMessage(formatMessage(e.getName(), e.getMessage()));

        return badRequestException(badRequestException);
    }

    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity<String> httpServerErrorException(HttpServerErrorException e) {
        return ResponseEntity.status(e.getStatusCode())
                             .body(e.getResponseBodyAsString());
    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<String> httpClientErrorException(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode())
                             .body(e.getResponseBodyAsString());
    }

    private String formatMessage(String name, String message) {
        return "[%s] %s".formatted(name, message);
    }
}
