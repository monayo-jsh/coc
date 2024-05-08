package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.common.AcademeClan;
import open.api.coc.clans.database.entity.ClanEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanResponse {

    private String tag;
    private String name;

    private Integer clanLevel;
    private Integer clanPoints;
    private IconUrlResponse badgeUrls;
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

    private LabelResponse warLeague;
    private List<LabelResponse> labels;

    private LabelResponse capitalLeague;
    private Integer clanCapitalPoints;
    private ClanCapitalResponse clanCapital;

    private Integer members;
    private List<ClanMemberResponse> memberList;

    private Integer order;
    public static ClanResponse create(AcademeClan clan) {
        return ClanResponse.builder()
                           .name(clan.getName())
                           .tag(clan.getTag())
                           .build();
    }

    public static ClanResponse create(ClanEntity clan) {
        return ClanResponse.builder()
                           .tag(clan.getTag())
                           .name(clan.getName())
                           .order(clan.getOrder())
                           .build();
    }
}
