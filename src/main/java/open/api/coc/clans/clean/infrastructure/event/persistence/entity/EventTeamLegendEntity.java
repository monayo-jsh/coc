package open.api.coc.clans.clean.infrastructure.event.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_event_team_legend"
)
@Comment("이벤트 팀 전설내기 테이블")
public class EventTeamLegendEntity {

    @Comment("팀 고유키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("팀 이름")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Comment("이벤트 정보")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id", nullable = false, updatable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private EventEntity event;

    @Comment("팀원 목록")
    @OneToMany(fetch = LAZY, mappedBy = "teamLegend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventTeamLegendMemberEntity> members;

    // 기본값 설정을 위한 빌더 객체
    public static class EventTeamLegendEntityBuilder {
        private List<EventTeamLegendMemberEntity> members = new ArrayList<>();
    }

}
