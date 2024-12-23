package open.api.coc.clans.clean.application.event;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import open.api.coc.clans.clean.application.event.mapper.EventUseCaseMapper;
import open.api.coc.clans.clean.domain.event.model.EventTeam;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.domain.event.service.EventTeamLegendService;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamLegendResponse;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamRankResponse;
import open.api.coc.clans.domain.players.PlayerResponse;
import open.api.coc.clans.service.PlayersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventUseCase {

    private final EventTeamLegendService eventTeamLegendService;

    private final PlayersService playersService;

    private final EventUseCaseMapper useCaseMapper;

    @Transactional(readOnly = true)
    public EventTeamLegendResponse getLatestTeamLegend() {
        // 최신 팀 전설내기 이벤트 정보를 조회한다.
        EventTeamLegend teamLegend = eventTeamLegendService.findLatestTeamLegend();

        // 응답한다.
        return useCaseMapper.toEventTeamLegendResponse(teamLegend);
    }

    @Transactional
    public void processForTeamLegendRecord() {
        // 최신 팀 전설내기 이벤트 정보를 조회한다.
        EventTeamLegend teamLegend = eventTeamLegendService.findLatestTeamLegend();

        // 이미 종료된 이벤트인 경우 종료
        if (teamLegend.isFinish()) return;

        // 이벤트 시작일이 되지 않은 경우
        if (teamLegend.isNotStartDate()) return;

        // 이벤트 시작일시가 지났으나 진행중 상태가 아닌 경우
        if (teamLegend.isNotStarted()) {
            teamLegend.start();
        }

        // 이벤트 데이터를 현행화한다.
        processSyncAllTeamMembers(teamLegend);

        // 종료일시가 지난경우 이벤트 상태 종료 처리.
        if (teamLegend.isPassedEndDate()) {
            teamLegend.finish();
        }

        // 이벤트 저장
        eventTeamLegendService.update(teamLegend);
    }

    private void processSyncAllTeamMembers(EventTeamLegend teamLegend) {
        // 리팩토링이 종료된 시점에는 트로피만 현행화되도록 수정하면 좋을듯
        List<EventTeamMember> allTeamMembers = teamLegend.getTeams()
                                                         .stream()
                                                         .flatMap(team -> team.getMembers().stream())
                                                         .toList();

        for (EventTeamMember member : allTeamMembers) {
            try {
                PlayerResponse playerResponse = playersService.findPlayerBy(member.getTag());
                member.refreshInfo(playerResponse.getName(), playerResponse.getTrophies());
            } catch (Exception e) {
                log.error("%s (%s) 정보 동기화 실패".formatted(member.getName(), member.getTag()), e);
            }
        }
    }

    @Transactional
    public void processForTeamLegendRecordKeeping() {
        eventTeamLegendService.saveCurrentTeamLegendRecord();
    }

    @Transactional(readOnly = true)
    public List<EventTeamRankResponse> getTeamLegendDailyRankings(Long eventId) {
        // 이벤트 전설내기를 조회한다.
        EventTeamLegend eventTeamLegend = eventTeamLegendService.findById(eventId);

        // 참여 팀 아이디 목록을 구한다.
        List<Long> teamIds = eventTeamLegend.getTeams().stream().map(EventTeam::getId).toList();

        // 배정된 팀이 없는 경우
        if (teamIds.isEmpty()) { return Collections.emptyList(); }

        // 참여팀의 일별 결과를 조회한다.
        return eventTeamLegendService.findAllTeamLegendDailyRankingsByIds(teamIds);
    }
}
