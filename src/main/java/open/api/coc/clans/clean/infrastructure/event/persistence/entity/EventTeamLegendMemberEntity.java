package open.api.coc.clans.clean.infrastructure.event.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "tb_event_team_legend_member"
)
@Comment("이벤트 팀 전설내기 멤버 테이블")
public class EventTeamLegendMemberEntity implements Persistable<String> {

    @Comment("플레이어 태그")
    @Id
    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Comment("플레이어 이름")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Comment("트로피 점수")
    @Column(name = "trophies", nullable = false)
    @ColumnDefault("0")
    private Integer trophies;

    @Comment("타운홀 레벨")
    @Column(name = "townhall_level", nullable = false)
    @ColumnDefault("0")
    private Integer townhallLevel;

    @Comment("팀 정보")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_legend_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private EventTeamLegendEntity team;

    @Comment("멤버 등록일시")
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Comment("멤버 수정일시")
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private EventTeamLegendMemberEntity(String tag, String name, Integer trophies, Integer townhallLevel,
                                       EventTeamLegendEntity team, LocalDateTime createdAt,
                                       LocalDateTime updatedAt, boolean isNew) {
        this.tag = tag;
        this.name = name;
        this.trophies = trophies;
        this.townhallLevel = townhallLevel;
        this.team = team;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isNew = isNew;
    }

    @Transient
    private boolean isNew;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.tag;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void changeTeam(EventTeamLegendEntity team) {
        this.team = team;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class EventTeamLegendMemberEntityBuilder {
        private boolean isNew = false;
    }
}
