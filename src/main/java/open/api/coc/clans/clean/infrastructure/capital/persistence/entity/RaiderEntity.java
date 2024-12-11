package open.api.coc.clans.clean.infrastructure.capital.persistence.entity;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tb_raider")
@Comment("캐피탈 참여 테이블")
public class RaiderEntity {

    @Comment("캐피팔 참여 고유키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raider_seq", nullable = false)
    private Long id;

    @Comment("사용자 태그")
    @Column(name = "tag", nullable = false)
    private String tag;

    @Comment("사용자 이름")
    @Column(name = "name", nullable = false)
    private String name;

    @Comment("캐피탈 공격수")
    @Column(name = "attacks", nullable = false)
    private Integer attacks;

    @Comment("캐피탈 획득 점수")
    @Column(name = "resourceLooted", nullable = false)
    private Integer resourceLooted;

    @Comment("캐피탈 고유키 (참조)")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "raid_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private RaidEntity raid;

    @Builder
    public RaiderEntity(Long id, String tag, String name, Integer attacks, Integer resourceLooted, RaidEntity raid) {
        this.id = id;
        this.tag = tag;
        this.name = name;
        this.attacks = attacks;
        this.resourceLooted = resourceLooted;
        this.raid = raid;
    }

    public void changeRaid(RaidEntity raid) {
        this.raid = raid;
    }
}
