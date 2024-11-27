package open.api.coc.clans.clean.domain.season.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import open.api.coc.clans.common.exception.BadRequestException;

public class SeasonDuplicateException extends BadRequestException {

    public SeasonDuplicateException(LocalDate seasonEndDate) {
        super("이미 등록된 시즌 종료일: %s".formatted(seasonEndDate.format(DateTimeFormatter.ISO_DATE)));
    }

}
