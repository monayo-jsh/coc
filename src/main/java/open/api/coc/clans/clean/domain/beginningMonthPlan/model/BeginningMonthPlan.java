package open.api.coc.clans.clean.domain.beginningMonthPlan.model;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_beginning_month_plan"
)
@Comment("월초 일정 테이블")
public class BeginningMonthPlan {

    @Comment("월초 일정 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("월초 일정 날짜")
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Comment("리그전 일정 설명")
    @Column(name = "leagueWarDesc", nullable = true, length = 50)
    private String leagueWarDesc;

    @Comment("클랜전 일정 설명")
    @Column(name = "clanWarDesc", nullable = true, length = 50)
    private String clanWarDesc;

    @Comment("캐피탈 일정 설명")
    @Column(name = "capitalDesc", nullable = true, length = 50)
    private String capitalDesc;

    @Comment("월초 고유키")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "beginning_month_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private BeginningMonth beginningMonth;

    @Builder
    public BeginningMonthPlan(Long id, LocalDate date, String leagueWarDesc, String clanWarDesc,
                              String capitalDesc, BeginningMonth beginningMonth) {
        this.id = id;
        this.date = date;
        this.leagueWarDesc = leagueWarDesc;
        this.clanWarDesc = clanWarDesc;
        this.capitalDesc = capitalDesc;
        this.beginningMonth = beginningMonth;
    }

    public void changeBeginningMonth(BeginningMonth beginningMonth) {
        this.beginningMonth = beginningMonth;
    }
}
