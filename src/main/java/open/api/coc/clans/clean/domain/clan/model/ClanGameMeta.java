package open.api.coc.clans.clean.domain.clan.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
public class ClanGameMeta {

    @Comment("진행월: yyyy-MM")
    private String progressDate;

    @Comment("완료 점수")
    private Integer completedPoint;

    @Builder
    private ClanGameMeta(String progressDate, Integer completedPoint) {
        this.progressDate = progressDate;
        this.completedPoint = completedPoint;
    }
}
