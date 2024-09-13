package open.api.coc.clans.clean.domain.competition.exception;

public class CompetitionAlreadyExistsException extends RuntimeException {

    public CompetitionAlreadyExistsException(String message) {
        super("이미 등록된 대회가 존재합니다. (%s)".formatted(message));
    }

}
