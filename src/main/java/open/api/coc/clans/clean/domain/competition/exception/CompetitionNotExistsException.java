package open.api.coc.clans.clean.domain.competition.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class CompetitionNotExistsException extends NotFoundException {

    public CompetitionNotExistsException(Long id) {
        super("대회: %s".formatted(id));
    }

}
