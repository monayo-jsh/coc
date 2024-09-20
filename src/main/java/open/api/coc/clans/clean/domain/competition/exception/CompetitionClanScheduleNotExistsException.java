package open.api.coc.clans.clean.domain.competition.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.clans.common.exception.NotFoundException;

public class CompetitionClanScheduleNotExistsException extends NotFoundException {

    public CompetitionClanScheduleNotExistsException(Long clanScheduleId) {
        super(ExceptionCode.ERROR_NOT_FOUND);
        addExtraMessage("참여 클랜 일정 고유키 일치하지 않음: %s".formatted(clanScheduleId));
    }

}
