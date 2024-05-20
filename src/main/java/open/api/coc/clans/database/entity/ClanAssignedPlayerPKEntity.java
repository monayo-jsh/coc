package open.api.coc.clans.database.entity;

import jakarta.persistence.Column;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ClanAssignedPlayerPKEntity implements Serializable {

    @Column(name = "clan_tag", length = 100)
    private String clanTag;

    @Column(name = "season_date", nullable = false, length = 6)
    private String seasonDate;

    @Column(name = "player_tag", nullable = false, length = 100)
    private String playerTag;

}
