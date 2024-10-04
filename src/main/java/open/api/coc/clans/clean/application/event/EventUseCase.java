package open.api.coc.clans.clean.application.event;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.application.event.mapper.EventUseCaseMapper;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.domain.event.service.EventTeamLegendService;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamLegendResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventUseCase {

    private final EventTeamLegendService eventTeamLegendService;

    private final EventUseCaseMapper useCaseMapper;

    @Transactional(readOnly = true)
    public EventTeamLegendResponse getLatestTeamLegend() {
        // 현재 진행중인 팀 전설내기 이벤트 정보를 조회한다.
        EventTeamLegend teamLegend = eventTeamLegendService.findLatestTeamLegend();

        // 응답한다.
        return useCaseMapper.toEventTeamLegendResponse(teamLegend);
    }
}
