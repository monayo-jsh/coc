package open.api.coc.clans.clean.domain.event.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.event.exception.EventTeamLegendNotExistsException;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendMemberRepository;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventTeamLegendService {

    private final EventTeamLegendRepository teamLegendRepository;
    private final EventTeamLegendMemberRepository teamLegendMemberRepository;

    @Transactional(readOnly = true)
    public EventTeamLegend findLatestTeamLegend() {
        LocalDateTime startDate = teamLegendRepository.findLatestStartDate();
        if (startDate == null) {
            throw new EventTeamLegendNotExistsException();
        }

        return teamLegendRepository.findByStartDate(startDate)
                                   .orElseThrow(() -> new EventTeamLegendNotExistsException(startDate));
    }

    @Transactional
    public void update(EventTeamLegend teamLegend) {
        teamLegendRepository.save(teamLegend);
    }

    @Transactional
    public void saveCurrentTeamLegendRecord() {
        EventTeamLegend eventTeamLegend = findLatestTeamLegend();

        if (eventTeamLegend.isFinish()) return; // 종료된 경우

        List<EventTeamMember> allMembers = eventTeamLegend.getTeams()
                                                    .stream()
                                                    .flatMap(team -> team.getMembers().stream())
                                                    .toList();

        if (allMembers.isEmpty()) return; // 참여자가 없으면 이력 생성 안해도 됨.

        teamLegendMemberRepository.saveRecordHistory(allMembers);
    }

    @Transactional(readOnly = true)
    public EventTeamLegend findById(Long eventId) {
        return teamLegendRepository.findById(eventId)
                                   .orElseThrow(() -> new EventTeamLegendNotExistsException(eventId));
    }

//    @Transactional(readOnly = true)
//    public List<EventTeamMemberHistory> findLastTeamMemberHistory(List<Long> teamIds) {
//        return teamLegendMemberRepository.findByMemberHistory(teamIds);
//    }
}