package open.api.coc.external.coc.clan.domain.clan;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import open.api.coc.external.coc.clan.domain.common.Label;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Clan {

    private String tag;
    private String name;
    private Integer clanLevel;
    private Integer clanPoints;
    private IconUrl badgeUrls;
    private String description;

    private String type;
    private Boolean isFamilyFriendly;
    private Boolean isWarLogPublic;
    private String warFrequency;
    private Integer requiredTownhallLevel;
    private Integer requiredTrophies;

    private Integer warWinStreak;
    private Integer warWins;
    private Integer warLosses;
    private Integer warTies;
    
    private Label warLeague;
    private List<Label> labels;

    private Label capitalLeague;
    private Integer clanCapitalPoints;
    private ClanCapital clanCapital;

    private Integer members;
    private List<ClanMember> memberList;

    public String getWarLeagueName() {
        if (Objects.isNull(warLeague)) return null;
        return warLeague.getName();
    }
}
