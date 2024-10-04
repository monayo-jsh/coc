package open.api.coc.clans.clean.domain.event.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EventTeamLegend {

    private final String type = "TEAM_LEGEND"; // 팀 전설내기

    private Long id; // 이벤트 고유키
    private String name; // 이벤트 이름
    private LocalDateTime startDate; // 이벤트 시작일시
    private LocalDateTime endDate; // 이벤트 종료일시
    private EventStatus status;

    private List<EventTeam> teams; // 팀 목록

    public static class EventTeamLegendBuilder {
        private List<EventTeam> teams = new ArrayList<>();
    }
}
