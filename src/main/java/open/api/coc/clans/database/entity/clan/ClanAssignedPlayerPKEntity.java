package open.api.coc.clans.database.entity.clan;

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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class ClanAssignedPlayerPKEntity implements Serializable {

    @Column(name = "season_date", nullable = false, length = 6)
    private String seasonDate;

    @Column(name = "player_tag", nullable = false, length = 100)
    private String playerTag;

}
