package open.api.coc.clans.database.entity.player;

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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "tb_player_record_history",
    indexes = {
        @Index(name = "idx_record_history_tag_recorded_at", columnList = "tag, recorded_at")
    }
)
@Comment("플레이어 기록 이력 테이블")
public class PlayerRecordHistoryEntity {

    @Comment("기록 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("플레이어 태그")
    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Comment("이전 트로피 점수")
    @Column(name = "old_trophies", nullable = false)
    @ColumnDefault("0")
    private Integer oldTrophies;

    @Comment("변경 트로피 점수")
    @Column(name = "new_trophies", nullable = false)
    @ColumnDefault("0")
    private Integer newTrophies;

    @Comment("이전 공격 성공수")
    @Column(name = "old_attack_wins", nullable = false)
    @ColumnDefault("0")
    private Integer oldAttackWins;

    @Comment("변경 공격 성공수")
    @Column(name = "new_attack_wins", nullable = false)
    @ColumnDefault("0")
    private Integer newAttackWins;

    @Comment("이전 방어 성공수")
    @Column(name = "old_defence_wins", nullable = false)
    @ColumnDefault("0")
    private Integer oldDefenceWins;

    @Comment("변경 방어 성공수")
    @Column(name = "new_defence_wins", nullable = false)
    @ColumnDefault("0")
    private Integer newDefenceWins;

    @Comment("기록일시")
    @Column(name = "recorded_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime recordedAt;

    @Builder
    private PlayerRecordHistoryEntity(Long id, String tag, Integer oldTrophies, Integer newTrophies,
                                     Integer oldAttackWins, Integer newAttackWins,
                                     Integer oldDefenceWins, Integer newDefenceWins,
                                     LocalDateTime recordedAt) {
        this.id = id;
        this.tag = tag;
        this.oldTrophies = oldTrophies;
        this.newTrophies = newTrophies;
        this.oldAttackWins = oldAttackWins;
        this.newAttackWins = newAttackWins;
        this.oldDefenceWins = oldDefenceWins;
        this.newDefenceWins = newDefenceWins;
        this.recordedAt = recordedAt;
    }

}
