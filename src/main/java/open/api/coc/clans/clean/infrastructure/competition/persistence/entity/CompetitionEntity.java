package open.api.coc.clans.clean.infrastructure.competition.persistence.entity;

import static jakarta.persistence.FetchType.LAZY;

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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@Entity
@Table(
    name = "tb_comp",
    indexes = {
        @Index(name = "idx_compeition_start_date", columnList = "start_date")
    }
)
@Comment("대회 테이블")
public class CompetitionEntity {

    @Comment("대회 고유키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("대회 이름")
    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Comment("대회 시작일")
    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Comment("대회 종료일")
    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Comment("디스코드 URL")
    @Column(name = "discord_url", nullable = true, length = 500)
    private String discordUrl;

    @Comment("룰북 URL")
    @Column(name = "rule_book_url", nullable = true, length = 500)
    private String ruleBookUrl;

    @Comment("최대 등록 멤버수")
    @Column(name = "roaster_size", nullable = true)
    private Integer roasterSize;

    @Comment("제한 사항 (밴 등)")
    @Column(name = "restrictions", nullable = true, length = 500)
    private String restrictions;

    @Comment("대회 색상")
    @Column(name = "bg_color", nullable = true, length = 20)
    private String bgColor;

    @Comment("메모")
    @Column(name = "remarks", nullable = true, length = 4000)
    private String remarks;

    @Comment("대회 참가 목록")
    @OneToMany(fetch = LAZY, mappedBy = "competition")
    @Builder.Default
    private List<CompetitionClanEntity> participantClans = new ArrayList<>();

}
