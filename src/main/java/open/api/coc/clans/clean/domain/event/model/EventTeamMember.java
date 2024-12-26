package open.api.coc.clans.clean.domain.event.model;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class EventTeamMember {

    private Long id; // 팀 멤버 고유키
    private Long teamId; // 팀 고유키

    private String tag; //플레이어 태그
    private String name; // 플레이어 이름

    private int trophies; // 트로피 점수
    private int townhallLevel; // 타운홀 레벨

    private LocalDateTime updatedAt; // 최종 수정일시

    public void refreshInfo(String name, Integer trophies, Integer townhallLevel) {
        this.name = name;
        this.trophies = trophies;
        this.townhallLevel = townhallLevel;
    }
}
