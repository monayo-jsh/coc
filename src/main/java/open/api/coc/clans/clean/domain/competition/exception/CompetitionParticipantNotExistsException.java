package open.api.coc.clans.clean.domain.competition.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class CompetitionParticipantNotExistsException extends NotFoundException {

    public CompetitionParticipantNotExistsException(String clanTag) {
        super("대회 참가 클랜: %s".formatted(clanTag));
    }

}
