package open.api.coc.clans.common.exception.handler;

import jakarta.validation.ConstraintViolationException;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.clans.common.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<String> noHandlerFoundException(NoHandlerFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(e.getMessage());
    }

    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> badRequestException(BadRequestException e) {
        String responseBody = formatMessage(e.getCode(), e.getMessage());
        return ResponseEntity.badRequest()
                             .body(responseBody);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFoundException(NotFoundException e) {
        String responseBody = formatMessage(e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(responseBody);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER)
                                                                     .addExtraMessage(e.getMessage());

        return badRequestException(badRequestException);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<String> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER)
                                                                     .addExtraMessage(formatMessage(e.getParameterName(), " must not be null"));

        return badRequestException(badRequestException);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException e) {
        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER);
        e.getConstraintViolations().forEach(constraintViolation -> {
            badRequestException.addExtraMessage(constraintViolation.getMessage());
        });

        return badRequestException(badRequestException);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER);
        e.getFieldErrors().forEach(error -> {
            badRequestException.addExtraMessage(formatMessage(error.getField(), error.getDefaultMessage()));
        });

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
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("[code]: %s, [error]: %s".formatted(e.getStatusCode(), e.getResponseBodyAsString()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exception(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("[error]: %s".formatted(e.getMessage()));
    }

    private String formatMessage(String name, String message) {
        return "[%s] %s".formatted(name, message);
    }
}
