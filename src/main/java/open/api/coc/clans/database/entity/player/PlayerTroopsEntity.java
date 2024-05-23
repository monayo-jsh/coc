package open.api.coc.clans.database.entity.player;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import open.api.coc.clans.database.entity.player.common.PlayerItemEntity;
import open.api.coc.clans.database.entity.player.common.PlayerItemPKEntity;
import open.api.coc.clans.database.entity.player.common.TroopType;
import org.springframework.data.domain.Persistable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_player_troops")
public class PlayerTroopsEntity implements Persistable<PlayerItemPKEntity> {

    @EmbeddedId
    private PlayerItemPKEntity id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TroopType type;

    @Embedded
    private PlayerItemEntity levelInfo;

    @MapsId(value = "playerTag")
    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "player_tag", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private PlayerEntity player;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public PlayerItemPKEntity getId() {
        return this.id;
    }

    public void changePlayer(PlayerEntity player) {
        this.player = player;
    }

    public boolean isPet() {
        return Objects.equals(type, TroopType.PET);
    }
    public boolean isSiegeMachine() {
        return Objects.equals(type, TroopType.SIEGE_MACHINE);
    }
}
