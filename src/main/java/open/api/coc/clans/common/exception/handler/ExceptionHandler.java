package open.api.coc.clans.common.exception.handler;

import open.api.coc.clans.common.exception.NotFoundException;

public class ExceptionHandler {

    private ExceptionHandler() {
        //인스턴스화 방지
    }

    public static void throwNotFoundException(String notFoundName) {
        throw createNotFoundException(notFoundName);
    }

    public static NotFoundException createNotFoundException(String notFoundName) {
        return NotFoundException.create(notFoundName);
    }

}
