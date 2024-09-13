package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    name = "tb_comp_clan_schedule"
)
@Comment("대회 참여 클랜 일정 테이블")
public class CompetitionClanScheduleEntity {

    @Comment("대회 참여 클랜 일정 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("대회 참여 클랜 유니크키(참조)")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comp_clan_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)) // 외래키 제약 조건 설정하지 않음
    private CompetitionClanEntity competitionClan;

    @Comment("라운드 설명")
    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Comment("대회 시작일")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Comment("대회 종료일")
    @Column(name = "end_date", nullable = false)
    private LocalDate end_date;

    @Comment("대회 확정일시 (시작~종료일 중 네고된 일시)")
    @Column(name = "fixed_at", nullable = true)
    private LocalDateTime fixedAt;

    // 대회 참여 클랜 실제 참가자 목록은 일정 엔티티에서 관리
    @Comment("대회 참여 클랜 실제 참가자 목록")
    @OneToMany(fetch = LAZY, mappedBy = "competitionClanSchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetitionClanRoundParticipantEntity> participants;

    @Builder
    private CompetitionClanScheduleEntity(Long id, CompetitionClanEntity competitionClan,
                                         String description, LocalDate startDate,
                                         LocalDate end_date, LocalDateTime fixedAt,
                                         List<CompetitionClanRoundParticipantEntity> participants) {
        this.id = id;
        this.competitionClan = competitionClan;
        this.description = description;
        this.startDate = startDate;
        this.end_date = end_date;
        this.fixedAt = fixedAt;
        this.participants = participants;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class CompetitionClanScheduleEntityBuilder {
        private List<CompetitionClanRoundParticipantEntity> participants = new ArrayList<>();
    }

}
