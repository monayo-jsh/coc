package open.api.coc.clans.clean.domain.beginningMonthPlan.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_beginning_month",
    indexes = {
        @Index(name = "idx_beginning_month_beginning_month", columnList = "beginning_month"),
    }
)
@Comment("월초 테이블")
public class BeginningMonth {

    @Comment("월초 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("월 날짜")
    @Column(name = "beginning_month", nullable = false)
    private LocalDate month;

    @Comment("월초 일정 목록")
    @OneToMany(fetch = LAZY, mappedBy = "beginningMonth", cascade = CascadeType.ALL)
    private List<BeginningMonthPlan> plans;

    @Builder
    public BeginningMonth(Long id, LocalDate month, List<BeginningMonthPlan> plans) {
        this.id = id;
        this.month = month;

        this.plans = new ArrayList<>();
        addPlans(plans);
    }

    private void addPlans(List<BeginningMonthPlan> plans) {
        this.plans.clear();
        plans.forEach(this::addPlan);
    }

    private void addPlan(BeginningMonthPlan beginningMonthPlan) {
        beginningMonthPlan.changeBeginningMonth(this);
        this.plans.add(beginningMonthPlan);
    }

}
