package open.api.coc.clans.clean.domain.event.model;

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
public class EventTeam {

    private Long eventId; // 이벤트 고유키

    private Long id; // 팀 고유키
    private String name; // 팀 이름

    private int trophies; // 팀 트로피

    private List<EventTeamMember> members; // 팀원 목록

    public static class EventTeamBuilder {
        private int trophies = 0; // 팀 총 트로피 점수
        private List<EventTeamMember> members = new ArrayList<>();

        public void syncTotalTrophies() {
            trophies = this.members.stream().mapToInt(EventTeamMember::getTrophies).sum();
        }
    }
}
