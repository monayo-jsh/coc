package open.api.coc.clans.clean.domain.event.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class EventTeamLegendNotExistsException extends NotFoundException {

    public EventTeamLegendNotExistsException() {
        super("등록된 팀 전설내기 없음");
    }

    public EventTeamLegendNotExistsException(Long id) {
        super("등록된 팀 전설내기(%s) 없음".formatted(id));
    }

}
