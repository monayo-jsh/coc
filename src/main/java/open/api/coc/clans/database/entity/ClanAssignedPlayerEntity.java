package open.api.coc.clans.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
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
@Entity(name = "tb_clan_assigned_player")
public class ClanAssignedPlayerEntity implements Persistable<ClanAssignedPlayerPKEntity> {

    @EmbeddedId
    private ClanAssignedPlayerPKEntity id;

    @Column(name = "clan_tag", length = 100)
    private String clanTag;

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

}
