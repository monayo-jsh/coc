package open.api.coc.clans.clean.domain.player.model;

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
public class PlayerRecordHistory {

    private String id; // 기록 고유키
    private String tag; // 플레이어 태그
    private String name; // 플레이어 이름

    private Integer oldTrophies; // 이전 트로피 점수
    private Integer newTrophies; // 변경 트로피 점수

    private Integer oldAttackWins; // 이전 공격 성공수
    private Integer newAttackWins; // 변경 공격 성공수

    private Integer oldDefenceWins; // 이전 방어 성공수
    private Integer newDefenceWins; // 변경 방어 성공수

    private LocalDateTime recordedAt; // 기록일시

}
