package open.api.coc.clans.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_clan_content")
public class ClanContentEntity {

    @Id
    @Column(name = "tag", length = 100)
    private String tag;

    @Column(name = "clan_war_yn", nullable = false, length = 1)
    private String clanWarYn;

    @Column(name = "war_league_yn", nullable = false, length = 1)
    private String warLeagueYn;

    @Column(name = "clan_capital_yn", nullable = false, length = 1)
    private String clanCapitalYn;

    @Column(name = "clan_war_parallel_yn", nullable = false, length = 1)
    private String clanWarParallelYn;

}
