package open.api.coc.clans.database.entity.player.common;

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
public class PlayerItemPKEntity implements Serializable {

    @Column(name = "player_tag", length = 100)
    private String playerTag;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public static PlayerItemPKEntity create(String tag, String name) {
        return PlayerItemPKEntity.builder()
                                 .playerTag(tag)
                                 .name(name)
                                 .build();
    }
}
