package open.api.coc.clans.clean.domain.competition.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class CompetitionClanScheduleDuplicateException extends BadRequestException {

    public CompetitionClanScheduleDuplicateException(LocalDate startDate) {
        super(ExceptionCode.ALREADY_DATA);
        addExtraMessage("라운드 시작 일정 존재: %s".formatted(startDate.format(DateTimeFormatter.ISO_DATE)));
    }

}
