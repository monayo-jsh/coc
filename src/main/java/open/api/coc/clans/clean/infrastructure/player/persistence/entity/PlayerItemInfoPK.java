package open.api.coc.clans.clean.infrastructure.player.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class PlayerItemInfoPK implements Serializable {

    @Column(name = "player_tag", length = 100)
    private String playerTag;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public static PlayerItemInfoPK create(String tag, String name) {
        return PlayerItemInfoPK.builder()
                               .playerTag(tag)
                               .name(name)
                               .build();
    }
}
