package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.common.IconUrl;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeagueWarClan {
    private String tag;
    private String name;
    private IconUrl badgeUrls;
    private Integer clanLevel;
    private Integer attacks;
    private Integer starts;
    private float destructionPercentage;
    private List<LeagueWar> members;
}
