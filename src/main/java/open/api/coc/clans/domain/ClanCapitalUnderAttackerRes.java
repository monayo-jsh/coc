package open.api.coc.clans.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class ClanCapitalUnderAttackerRes {

    private final String name;
    private final List<ClanCapitalRaidSeasonMemberRes> attackers;

    private ClanCapitalUnderAttackerRes(String clanName, List<ClanCapitalRaidSeasonMemberRes> attackers) {
        this.name = clanName;
        this.attackers = attackers;
    }

    public static ClanCapitalUnderAttackerRes create(String clanName, List<ClanCapitalRaidSeasonMemberRes> attackers) {
        return new ClanCapitalUnderAttackerRes(clanName, attackers);
    }

}
