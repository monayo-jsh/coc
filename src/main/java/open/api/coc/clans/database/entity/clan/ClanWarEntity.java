package open.api.coc.clans.database.entity.clan;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tb_clan_war",
    indexes = {
        @Index(name = "TCW_IDX_01", columnList = "start_time, clan_tag"),
        @Index(name = "TCW_IDX_02", columnList = "clan_tag")
    }
)
public class ClanWarEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "war_id")
    private Long warId;

    @Column(name = "state")
    private String state;

    @Column(name = "type", updatable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ClanWarType type = ClanWarType.NONE;

    @Builder.Default
    @Column(name = "bttle_type")
    private String battleType = "none";

    @Column(name = "clan_tag", length = 100)
    private String clanTag;

    @Column(name = "preparation_start_time")
    private LocalDateTime preparationStartTime;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "team_size")
    private Integer teamSize;

    @Column(name = "attacks_per_member")
    private Integer attacksPerMember;

    // 리그전 클전의 경우 warTag 알 수 있음
    @Column(name = "war_tag")
    private String warTag;

    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "clanWar", cascade = CascadeType.ALL)
    private List<ClanWarMemberEntity> members = new ArrayList<>();

    @Transient
    private ClanEntity clan;

    public void addMember(ClanWarMemberEntity clanWarMemberEntity) {
        this.members.add(clanWarMemberEntity);
        clanWarMemberEntity.changeClanWar(this);
    }

    public boolean isCollected() {
        return STATE_WAR_COLLECTED.equals(this.state);
    }

    public void changeStateWarCollected() {
        this.state = STATE_WAR_COLLECTED;
    }

    public static final String STATE_WAR_COLLECTED = "warCollected";

    public void changeClan(ClanEntity clan) {
        this.clan = clan;
    }

    public static ClanWarEntity empty() {
        return ClanWarEntity.builder().build();
    }

    public boolean isLeagueWar() {
        return Objects.equals(type, ClanWarType.LEAGUE);
    }
}
