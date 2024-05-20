package open.api.coc.clans.common.exception.handler;

import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException e) {
        String responseBody = "[%s] %s".formatted(e.getCode(), e.getMessage());
        return ResponseEntity.badRequest()
                             .body(responseBody);
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

}
