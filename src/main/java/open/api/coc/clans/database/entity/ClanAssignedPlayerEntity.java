package open.api.coc.clans.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_clan_assigned_player")
@IdClass(ClanAssignedPlayerPKEntity.class)
public class ClanAssignedPlayerEntity {

    @Id
    @Column(name = "clan_tag", length = 100)
    private String clanTag;

    @Id
    @Column(name = "season_date", nullable = false, length = 6)
    private String seasonDate;

    @Id
    @Column(name = "player_tag", nullable = false, length = 100)
    private String playerTag;

}
