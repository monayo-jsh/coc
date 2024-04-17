package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.Getter;

@Getter
public class ClanCapitalUnderAttackerRes {

    private final String name;
    private final List<ClanCapitalRaidSeasonMemberResponse> attackers;

    private ClanCapitalUnderAttackerRes(String clanName, List<ClanCapitalRaidSeasonMemberResponse> attackers) {
        this.name = clanName;
        this.attackers = attackers;
    }

    public static ClanCapitalUnderAttackerRes create(String clanName, List<ClanCapitalRaidSeasonMemberResponse> attackers) {
        return new ClanCapitalUnderAttackerRes(clanName, attackers);
    }

}
