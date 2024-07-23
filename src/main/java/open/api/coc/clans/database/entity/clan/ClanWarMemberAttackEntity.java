package open.api.coc.clans.database.entity.clan;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
import org.springframework.lang.NonNull;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tb_clan_war_member_attack",
    indexes = {
        @Index(name = "CWMA_IDX_01", columnList = "war_id, tag")
    }
)
public class ClanWarMemberAttackEntity implements Persistable<ClanWarMemberAttackPKEntity> {

    @EmbeddedId
    private ClanWarMemberAttackPKEntity id;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "destruction_percentage")
    private Integer destructionPercentage;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne(fetch = LAZY)
    @JoinColumns(
        value = {
            @JoinColumn(name = "war_id", referencedColumnName = "war_id", insertable = false, updatable = false),
            @JoinColumn(name = "tag", referencedColumnName = "tag", insertable = false, updatable = false)
        },
        foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    private ClanWarMemberEntity clanWarMember;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public @NonNull ClanWarMemberAttackPKEntity getId() {
        return this.id;
    }

    public void changeClanWarMember(ClanWarMemberEntity clanWarMemberEntity) {
        this.clanWarMember = clanWarMemberEntity;
    }
}
