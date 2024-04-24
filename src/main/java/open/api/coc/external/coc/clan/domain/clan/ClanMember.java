package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.common.Label;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanMember {

    private String name;
    private String tag;
    private String role;
    private Integer townHallLevel;
    private Integer expLevel;

    private Label league;
    private Integer trophies;
    private Integer clanRank;
    private Integer previousClanRank;
    private Integer donations;
    private Integer donationsReceived;

}
