package open.api.coc.clans.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class ClanCapitalUnderAttackerRes {

    private final String name;
    private final List<ClanCapitalRaidSeasonMemberRes> attackers;

    private ClanCapitalUnderAttackerRes(String clanTag, List<ClanCapitalRaidSeasonMemberRes> attackers) {
        this.name = ClanCapitalAttackerRes.CLAN_TAGS.get(clanTag);
        this.attackers = attackers;
    }

    public static ClanCapitalUnderAttackerRes create(String clanTag, List<ClanCapitalRaidSeasonMemberRes> attackers) {
        return new ClanCapitalUnderAttackerRes(clanTag, attackers);
    }

}
