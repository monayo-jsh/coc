package open.api.coc.clans.clean.domain.season.exception;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import open.api.coc.clans.common.exception.NotFoundException;

public class SeasonNotExistsException extends NotFoundException {

    public SeasonNotExistsException(LocalDate seasonEndDate) {
        super("존재하지 않는 시즌 종료일: %s".formatted(seasonEndDate.format(DateTimeFormatter.ISO_DATE)));
    }

}
