package open.api.coc.clans.clean.domain.competition.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class CompetitionClanRoasterAlreadyExistsException extends BadRequestException {

    public CompetitionClanRoasterAlreadyExistsException(String playerTag) {
        super(ExceptionCode.ALREADY_DATA);
        addExtraMessage("사용자 태그(%s)".formatted(playerTag));
    }

}
