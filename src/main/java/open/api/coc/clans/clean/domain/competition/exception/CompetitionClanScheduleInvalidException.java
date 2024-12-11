package open.api.coc.clans.clean.domain.competition.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class CompetitionClanScheduleInvalidException extends BadRequestException {

    public CompetitionClanScheduleInvalidException(String message) {
        super(ExceptionCode.INVALID_PARAMETER);
        addExtraMessage(message);
    }

}
