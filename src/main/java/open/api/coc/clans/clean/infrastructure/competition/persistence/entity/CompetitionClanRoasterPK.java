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
//@Comment("대회 참여 클랜 전체 등록 멤버 키")
public class CompetitionClanRoasterPK implements Serializable {

    @Comment("대회 참여 클랜 유니크키")
    @Column(name = "comp_clan_id", nullable = false)
    private Long compClanId;

    @Comment("플레이어 태그")
    @Column(name = "player_tag", nullable = false)
    private String playerTag;

    @Builder
    private CompetitionClanRoasterPK(Long compClanId, String playerTag) {
        this.compClanId = compClanId;
        this.playerTag = playerTag;
    }

}
