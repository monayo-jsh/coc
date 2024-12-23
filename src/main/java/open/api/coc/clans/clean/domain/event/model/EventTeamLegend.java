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

    public boolean isIng() {
        return EventStatus.ING.equals(this.status);
    }
    public boolean isFinish() {
        return EventStatus.FINISH.equals(this.status);
    }

    public void start() {
        this.status = EventStatus.ING;
    }

    public void finish() {
        this.status = EventStatus.FINISH;
    }

    public boolean isNotStarted() {
        if (isIng()) return false;
        return LocalDateTime.now().isAfter(this.startDate);
    }

    public boolean isNotStartDate() {
        // 시작 시간을 기준으로 1분전을 기준으로 판단
        LocalDateTime conditionStartDateTime = LocalDateTime.now().minusMinutes(1);
        return conditionStartDateTime.isBefore(this.startDate);
    }

    public boolean isPassedEndDate() {
        // 종료 시간을 기준으로 1분 전까지만 갱신
        LocalDateTime conditionEndDateTime = this.endDate.minusMinutes(1);
        return LocalDateTime.now().isAfter(conditionEndDateTime);
    }

    public static class EventTeamLegendBuilder {
        private List<EventTeam> teams = new ArrayList<>();
    }
}
