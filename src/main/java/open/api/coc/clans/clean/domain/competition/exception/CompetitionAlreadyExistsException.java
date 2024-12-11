package open.api.coc.clans.clean.domain.competition.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class CompetitionAlreadyExistsException extends BadRequestException {

    public CompetitionAlreadyExistsException(String message) {
        super(ExceptionCode.ALREADY_DATA);
        addExtraMessage(message);
    }

}
