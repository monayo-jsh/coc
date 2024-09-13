package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
//@Comment("대회 참여 클랜 라운드별 실제 참가자 키")
public class CompetitionClanRoundParticipantPK implements Serializable {

    @Comment("대회 참여 클랜 일정 고유키")
    @Column(name = "comp_clan_schedule_id", nullable = false)
    private Long compClanScheduleId;

    @Comment("플레이어 태그")
    @Column(name = "player_tag", nullable = false, length = 100)
    private String playerTag;

    @Builder
    private CompetitionClanRoundParticipantPK(Long compClanScheduleId, String playerTag) {
        this.compClanScheduleId = compClanScheduleId;
        this.playerTag = playerTag;
    }
}
