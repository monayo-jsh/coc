package open.api.coc.clans.clean.domain.event.exception;

import java.time.LocalDateTime;
import open.api.coc.clans.common.exception.NotFoundException;

public class EventTeamLegendNotExistsException extends NotFoundException {

    public EventTeamLegendNotExistsException() {
        super("등록된 팀 전설내기 없음");
    }

    public EventTeamLegendNotExistsException(Long id) {
        super("등록된 팀 전설내기(id: %s) 없음".formatted(id));
    }
    public EventTeamLegendNotExistsException(LocalDateTime startDate) {
        super("등록된 팀 전설내기(startDate: %s) 없음".formatted(startDate));
    }

}
