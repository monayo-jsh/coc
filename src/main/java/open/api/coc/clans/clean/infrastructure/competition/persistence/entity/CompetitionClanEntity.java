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
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    name = "tb_comp_clan",
    indexes = {
        @Index(name = "idx_comp_clan_clan_tag", columnList = "clan_tag", unique = true)
    }
)
@Comment("대회 참여 클랜 테이블")
public class CompetitionClanEntity {

    @Comment("대회 참여 클랜 유니크키")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("대회 고유키(참조)")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comp_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)) // 외래키 제약조건 설정하지 않음
    private CompetitionEntity competition;

    @Comment("클랜 태그")
    @Column(name = "clan_tag", nullable = false, length = 100)
    private String clanTag;

    @Comment("대회 참여 상태")
    @Column(name = "status", nullable = true, length = 20)
    private String status;

    // 전체 등록 멤버의 라이프 사이클은 대회 참여 클랜 엔티티에서 관리
    @Comment("전체 등록 멤버")
    @OneToMany(fetch = LAZY, mappedBy = "competitionClan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetitionClanRoasterEntity> roasters;

    // 대회 참여 클랜 일정 라이프 사이클은 대회 참여 클랜 엔티티에서 관리
    @Comment("대회 참여 클랜 일정")
    @OneToMany(fetch = LAZY, mappedBy = "competitionClan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetitionClanScheduleEntity> schedules;

    @Builder
    private CompetitionClanEntity(Long id, CompetitionEntity competition, String clanTag,
                                  List<CompetitionClanRoasterEntity> roasters,
                                  List<CompetitionClanScheduleEntity> schedules) {
        this.id = id;
        this.competition = competition;
        this.clanTag = clanTag;
        this.roasters = roasters;
        this.schedules = schedules;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class CompetitionClanEntityBuilder {

        private List<CompetitionClanRoasterEntity> roasters = new ArrayList<>();
        private List<CompetitionClanScheduleEntity> schedules = new ArrayList<>();

    }
}
