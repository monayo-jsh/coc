package open.api.coc.clans.database.entity.league;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import open.api.coc.clans.database.entity.common.IconUrlEntity;
import open.api.coc.clans.database.entity.player.PlayerEntity;
import org.springframework.data.domain.Persistable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_league")
public class LeagueEntity implements Persistable<Integer> {

    @Id
    @Column(name = "league_id", nullable = false, length = 255)
    private Integer id;

    @Column(name = "league_name", nullable = false, length = 255)
    private String name;

    @Embedded
    private IconUrlEntity iconUrl;

    @Builder.Default
    @OneToMany(mappedBy = "league")
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
    public Integer getId() {
        return this.id;
    }

    public void add(PlayerEntity player) {
        this.players.add(player);
    }

}
