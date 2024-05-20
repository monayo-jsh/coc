package open.api.coc.clans.database.entity.clan;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import open.api.coc.clans.database.entity.common.YnType;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import org.springframework.data.domain.Persistable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_clan")
public class ClanEntity implements Persistable<String> {

    @Id
    @Column(name = "tag", length = 100)
    private String tag;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "orders", nullable = false)
    private Integer order;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "visible_yn", nullable = false)
    private YnType visibleYn = YnType.N;

    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag")
    private ClanContentEntity clanContent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag")
    private ClanBadgeEntity badgeUrl;

    @Builder.Default
    @OneToMany(mappedBy = "clan")
    private List<PlayerEntity> players = new ArrayList<>();

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
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
        this.badgeUrl = iconUrl;
    }

    public void add(PlayerEntity player) {
        this.players.add(player);
    }
}
