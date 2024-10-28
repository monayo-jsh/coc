package open.api.coc.clans.clean.infrastructure.player.persistence.entity;

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
import open.api.coc.clans.database.entity.common.YnType;
import org.springframework.data.domain.Persistable;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_player_hero_equipment")
public class PlayerHeroEquipmentEntity implements Persistable<PlayerItemInfoPK> {

    @EmbeddedId
    private PlayerItemInfoPK id;

    @Embedded
    private PlayerItemInfo levelInfo;

    @Column(name = "target_hero_name", nullable = false)
    private String targetHeroName;

    @Enumerated(EnumType.STRING)
    @Column(name = "wear_yn", nullable = false)
    private YnType wearYn;

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
    public PlayerItemInfoPK getId() {
        return this.id;
    }

    public void changePlayer(PlayerEntity player) {
        this.player = player;
    }

    public boolean isEqualsHeroEquipmentName(String heroEquipmentName) {
        return Objects.equals(id.getName(), heroEquipmentName);
    }

    public boolean isEqualsHeroTargetName(String heroName) {
        return Objects.equals(this.targetHeroName, heroName);
    }

    public boolean isWear() {
        return Objects.equals(YnType.Y, this.wearYn);
    }
}
