package open.api.coc.clans.clean.domain.beginningMonthPlan.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class BeginningMonthAlreadyExistsException extends BadRequestException {

    public BeginningMonthAlreadyExistsException() {
        super(ExceptionCode.ALREADY_DATA.getCode(), "이미 등록된 월초 일정 존재");
    }

}
