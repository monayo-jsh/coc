package open.api.coc.clans.database.entity.clan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.data.domain.Persistable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_clan_content")
public class ClanContentEntity implements Persistable<String> {

    @Id
    @Column(name = "tag", length = 100)
    private String tag;

    @Column(name = "clan_war_yn", nullable = false, length = 1)
    private String clanWarYn;

    @Column(name = "war_league_yn", nullable = false, length = 1)
    private String warLeagueYn;

    @Column(name = "clan_capital_yn", nullable = false, length = 1)
    private String clanCapitalYn;

    @Column(name = "clan_war_parallel_yn", nullable = false, length = 1)
    private String clanWarParallelYn;

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

    public static ClanContentEntity empty(String tag) {
        return ClanContentEntity.builder()
                                .tag(tag)
                                .clanWarYn(YnType.N.name())
                                .warLeagueYn(YnType.N.name())
                                .clanCapitalYn(YnType.N.name())
                                .clanWarParallelYn(YnType.N.name())
                                .build();
    }
}
