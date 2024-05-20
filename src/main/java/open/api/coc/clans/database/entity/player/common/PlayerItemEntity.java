package open.api.coc.clans.database.entity.player.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
@EqualsAndHashCode
public class PlayerItemEntity {

    @Column(name = "level", nullable = false)
    private Integer level;

    @Column(name = "max_level", nullable = false)
    private Integer maxLevel;

}
