package open.api.coc.clans.clean.infrastructure.clan.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_clan_game_meta"
)
public class ClanGameMetaEntity  {

    @Comment("진행월: yyyy-MM")
    @Id
    @Column(name = "progress_date", nullable = false, length = 6, updatable = false)
    private String progressDate;

    @Comment("완료 점수")
    @Column(name = "completed_point", nullable = false, updatable = false)
    private Integer completedPoint;

    @Builder
    private ClanGameMetaEntity(String progressDate, Integer completedPoint) {
        this.progressDate = progressDate;
        this.completedPoint = completedPoint;
    }
}
