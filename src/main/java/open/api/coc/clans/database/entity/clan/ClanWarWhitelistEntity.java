package open.api.coc.clans.database.entity.clan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "tb_clan_war_whitelist",
    indexes = {
        @Index(name = "TCWW_IDX_01", columnList = "player_tag")
    }
)
public class ClanWarWhitelistEntity {

    @Id
    @Column(name = "player_tag", length = 100)
    private String playerTag;

}
