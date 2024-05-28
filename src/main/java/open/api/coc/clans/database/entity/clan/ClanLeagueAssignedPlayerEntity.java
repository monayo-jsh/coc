package open.api.coc.clans.database.entity.clan;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Persistable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tb_clan_league_assigned_player",
    indexes = {
        @Index(name = "TCLAP_IDX_01", columnList = "season_date, clan_tag")
    }
)
public class ClanLeagueAssignedPlayerEntity implements Persistable<ClanAssignedPlayerPKEntity> {

    @EmbeddedId
    private ClanAssignedPlayerPKEntity id;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clan_tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private ClanEntity clan;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public ClanAssignedPlayerPKEntity getId() {
        return id;
    }

    public String getPlayerTag() {
        return id.getPlayerTag();
    }

}
