package open.api.coc.clans.domain.clans;

import lombok.Getter;
import open.api.coc.clans.common.Clan;

@Getter
public class ClanCapitalAttackerRes {
    private final String name;
    private final String tag;
    private final Integer attackCount;

    private ClanCapitalAttackerRes(String clanTag, Integer attackCount) {
        Clan clan = Clan.findByTag(clanTag);

        this.tag = clan.getTag();
        this.name = clan.getName();
        this.attackCount = attackCount;
    }

    public static ClanCapitalAttackerRes empty(String clanTag) {
        return create(clanTag, 0);
    }

    public static ClanCapitalAttackerRes create(String clanTag, int attackCount) {
        return new ClanCapitalAttackerRes(clanTag, attackCount);
    }

    public Integer getRemainAttackCount() {
        final Integer AVAILABLE_ATTACK_COUNT = 50;
        return AVAILABLE_ATTACK_COUNT - attackCount;
    }
}
