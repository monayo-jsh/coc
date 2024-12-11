package open.api.coc.clans.clean.infrastructure.clan.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "tb_clan_game",
    indexes = {
        @Index(name = "idx_clan_game_progress_date", columnList = "progress_date"),
        @Index(name = "idx_clan_game_player_tag_progress_date", columnList = "player_tag, progress_date")
    }
)
public class ClanGameEntity {

    @Comment("클랜 게임 진행 고유키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("플레이어 태그")
    @Column(name = "player_tag", nullable = false, length = 100, updatable = false)
    private String playerTag;

    @Comment("진행월: yyyy-MM")
    @Column(name = "progress_date", nullable = false, length = 6, updatable = false)
    private String progressDate;

    @Comment("시작 점수")
    @Column(name = "start_point", nullable = false, updatable = false)
    private Integer startPoint;

    @Comment("진행 점수")
    @Column(name = "finish_point", nullable = false)
    private Integer finishPoint;

    @Comment("마지막 반영일시")
    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

    @Builder
    private ClanGameEntity(Long id, String playerTag, String progressDate, Integer startPoint, Integer finishPoint, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.playerTag = playerTag;
        this.progressDate = progressDate;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.lastModifiedAt = lastModifiedAt;
    }

}
