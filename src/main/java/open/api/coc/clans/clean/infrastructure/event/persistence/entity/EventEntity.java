package open.api.coc.clans.clean.infrastructure.event.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
    name = "tb_event",
    indexes = {
        @Index(name = "idx_event_start_date", columnList = "start_date")
    }
)
@Comment("이벤트 테이블")
public class EventEntity {

    @Comment("이벤트 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("이벤트 이름")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Comment("이벤트 유형")
    @Column(name = "type", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EventType type;

    @Comment("이벤트 시작일시")
    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Comment("이벤트 종료일시")
    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Comment("이벤트 상태")
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    @Comment("이벤트 등록일시")
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Comment("이벤트 수정일시")
    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Comment("팀 목록")
    @OneToMany(fetch = LAZY, mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventTeamLegendEntity> teams;

    // 기본값 설정을 위한 빌더 객체
    public static class EventEntityBuilder {
        private List<EventTeamLegendEntity> teams = new ArrayList<>();
    }
}
