package open.api.coc.clans.clean.infrastructure.event.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    name = "tb_event_team_legend_member_history"
)
@Comment("이벤트 팀 전설내기 멤버 테이블")
public class EventTeamLegendMemberHistoryEntity {

    @Comment("이력 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("플레이어 태그")
    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Comment("플레이어 이름")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Comment("트로피 점수")
    @Column(name = "trophies", nullable = false)
    @ColumnDefault("0")
    private Integer trophies;

    @Comment("팀 정보")
    @Column(name = "team_legend_id", nullable = false)
    private Long teamLegendId;

    @Comment("트로피 최종 변경일시")
    @Column(name = "last_modified_at", nullable = false, updatable = false)
    private LocalDateTime lastModifiedAt;

    @Comment("이력 생성일시")
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Builder
    private EventTeamLegendMemberHistoryEntity(Long id, String tag, String name, Integer trophies,
                                              Long teamLegendId, LocalDateTime lastModifiedAt,
                                              LocalDateTime createdAt) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.trophies = trophies;
        this.teamLegendId = teamLegendId;
        this.lastModifiedAt = lastModifiedAt;
        this.createdAt = createdAt;
    }

}
