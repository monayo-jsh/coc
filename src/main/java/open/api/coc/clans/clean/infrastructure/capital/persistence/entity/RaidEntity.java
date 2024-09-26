package open.api.coc.clans.clean.infrastructure.capital.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.database.entity.clan.ClanEntity;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.hibernate.annotations.Comment;

@Getter
@NoArgsConstructor
@Entity
@Table(
    name = "tb_raid",
    indexes = @Index(name = "idx_raid_start_date", columnList = "start_date")
)
@Comment("습격전 테이블")
public class RaidEntity {

    @Comment("습격전 고유키")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="RAID_ID", nullable = false)
    private Long id;

    @Comment("습격전 상태")
    @Column(name = "state", nullable = true)
    private String state;

    @Comment("습격전 시작일")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Comment("습격전 종료일")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Comment("클랜 태그")
    @Column(name = "clan_tag", nullable = false)
    private String clanTag;

    @OneToMany(mappedBy = "raid", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RaiderEntity> raiders;

    @Transient
    private ClanEntity clan;

    @Builder
    private RaidEntity(Long id, String state, LocalDate startDate, LocalDate endDate, String clanTag, List<RaiderEntity> raiders) {
        this.id = id;
        this.state = state;
        this.startDate = startDate;
        this.endDate = endDate;
        this.clanTag = clanTag;
        this.raiders = raiders;
    }

    // 기본값 설정을 위한 빌더 객체
    public static class RaidEntityBuilder {
        private List<RaiderEntity> raiders = new ArrayList<>();
    }

    public void changeClan(ClanEntity clan) {
        this.clan = clan;
    }

    public void addRaiders(List<RaiderEntity> raiders) {
        raiders.forEach(this::addRaider);
    }

    public void addRaider(RaiderEntity raider) {
        raider.changeRaid(this);
        this.raiders.add(raider);
    }

}
