package open.api.coc.clans.domain;

import java.util.List;
import lombok.Getter;
import open.api.coc.external.coc.clan.domain.ClanCapitalRaidSeasonMember;

@Getter
public class ClanCapitalUnderAttackerRes {

    private final String name;
    private final List<ClanCapitalRaidSeasonMember> attackers;

    private ClanCapitalUnderAttackerRes(String clanTag, List<ClanCapitalRaidSeasonMember> attackers) {
        this.name = ClanCapitalAttackerRes.CLAN_TAGS.get(clanTag);
        this.attackers = attackers;
    }

    public static ClanCapitalUnderAttackerRes create(String clanTag, List<ClanCapitalRaidSeasonMember> attackers) {
        return new ClanCapitalUnderAttackerRes(clanTag, attackers);
    }

}
