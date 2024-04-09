package open.api.coc.clans.common.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<String> httpClientErrorException(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode())
                             .body(e.getResponseBodyAsString());
    }

}
