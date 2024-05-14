package open.api.coc.clans.common.exception.handler;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.clans.common.exception.NotFoundException;

public class ExceptionHandler {

    private ExceptionHandler() {
        //인스턴스화 방지
    }

    public static void throwBadRequestException(ExceptionCode exceptionCode, String message) {
        throw createBadRequestException(exceptionCode, message);
    }

    public static BadRequestException createBadRequestException(ExceptionCode exceptionCode, String message) {
        return BadRequestException.create(exceptionCode, message);
    }

    public static BadRequestException createBadRequestException(String code, String message) {
        return BadRequestException.create(code, message);
    }

    public static void throwNotFoundException(String notFoundName) {
        throw createNotFoundException(notFoundName);
    }

    public static NotFoundException createNotFoundException(String notFoundName) {
        return NotFoundException.create(notFoundName);
    }

}
