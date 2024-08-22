package open.api.coc.clans.database.entity.clan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    name = "tb_clan_league_war",
    indexes = {
        @Index(name = "TCLW_IDX_01", columnList = "clan_tag, season"),
        @Index(name = "TCLW_IDX_02", columnList = "season")
    }
)
public class ClanLeagueWarEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "league_war_id")
    private Long leagueWarId;

    @Column(name = "clan_tag", nullable = false, length = 100, updatable = false)
    private String clanTag;

    @Column(name = "season", nullable = false, updatable = false)
    private String season;

    @Column(name = "war_league", nullable = false, length = 100, updatable = false)
    private String warLeague;

}
