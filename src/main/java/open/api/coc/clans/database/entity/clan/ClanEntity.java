package open.api.coc.clans.database.entity.clan;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import open.api.coc.clans.clean.infrastructure.player.persistence.entity.PlayerEntity;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter // 리팩토링 끝나면 제거
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tb_clan")
@Builder
public class ClanEntity implements Persistable<String> {

    @Id
    @Column(name = "tag", nullable = false, length = 100)
    private String tag;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "war_league", nullable = true, length = 100)
    private String warLeague;

    @Column(name = "capital_hall_level", nullable = true)
    private Integer capitalHallLevel;

    @Column(name = "capital_points", nullable = true)
    private Integer capitalPoints;

    @Column(name = "capital_league", nullable = true, length = 100)
    private String capitalLeague;

    @Column(name = "orders", nullable = false)
    private Integer order;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "visible_yn", nullable = false)
    private YnType visibleYn = YnType.N;

    @Builder.Default
    @Column(name = "is_max_war", nullable = true)
    private Boolean isMaxWar = false;

    @Builder.Default
    @Column(name = "is_occupy", nullable = true)
    private Boolean isOccupy = false;

    @Builder.Default
    @Column(name = "is_league_occupy", nullable = true)
    private Boolean isLeagueOccupy = false;

    @Column(name = "war_description", nullable = true)
    private String warDescription;

    @Column(name = "reg_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime regDate;

    // clanContent 1:1 관계 NULL 불가에 따라 Lazy 로드 가능하도록 외래키 조건 제거
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ClanContentEntity clanContent;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ClanBadgeEntity badgeUrl;

    @Builder.Default
    @OneToMany(mappedBy = "clan")
    private List<PlayerEntity> players = new ArrayList<>();

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    public void markNotNew() {
        this.isNew = false;
    }

    @Override
    public String getId() {
        return this.tag;
    }

    public void changeClanContent(ClanContentEntity clanContent) {
        this.clanContent = clanContent;
    }

    public void changeBadgeUrl(ClanBadgeEntity iconUrl) {
        iconUrl.changeTag(this.tag);
        this.badgeUrl = iconUrl;
    }

    public void add(PlayerEntity player) {
        this.players.add(player);
    }


    public boolean isNotUsed() {
        return !isUsed();
    }

    public boolean isUsed() {
        return YnType.Y.equals(this.visibleYn);
    }

    public void changeWarLeague(String warLeagueName) {
        this.warLeague = warLeagueName;
    }


}
