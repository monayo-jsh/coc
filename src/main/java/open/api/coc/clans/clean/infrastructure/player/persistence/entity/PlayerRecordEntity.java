package open.api.coc.clans.clean.infrastructure.player.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tb_player_record"
)
@Comment("플레이어 기록 설정 테이블")
public class PlayerRecordEntity implements Persistable<PlayerRecordPK> {

    @EmbeddedId
    private PlayerRecordPK id;

    @Comment("트로피 점수")
    @Column(name = "trophies", nullable = false)
    @ColumnDefault("0")
    private Integer trophies;

    @Comment("플레이어 정렬순서")
    @Column(name = "orders", nullable = true)
    private Integer order;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    public static PlayerRecordEntity create(PlayerRecordPK playerRecordPK, Integer trophies) {
        return PlayerRecordEntity.builder()
                                 .id(playerRecordPK)
                                 .trophies(trophies)
                                 .build();
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

    @Override
    public PlayerRecordPK getId() {
        return this.id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void changeTrophies(Integer trophies) {
        this.trophies = trophies;
    }
    public void changeOrder(Integer order) {
        this.order = order;
    }

}
