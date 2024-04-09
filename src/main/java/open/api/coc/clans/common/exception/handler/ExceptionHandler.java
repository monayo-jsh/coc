package open.api.coc.clans.common.exception.handler;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;

public class ExceptionHandler {

    private ExceptionHandler() {
        //인스턴스화 방지
    }

    public static void throwNotFoundException(String notFoundName) {
        throw createNotFoundException(notFoundName);
    }

    public static CustomRuntimeException createNotFoundException(String notFoundName) {
        return CustomRuntimeException.create(ExceptionCode.ERROR_NOT_FOUND, notFoundName);
    }

}
