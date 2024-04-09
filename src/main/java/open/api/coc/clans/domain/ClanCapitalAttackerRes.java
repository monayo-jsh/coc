package open.api.coc.clans.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class ClanCapitalAttackerRes {
    private final String name;
    private final String tag;
    private final Integer attackCount;

    public static final Map<String, String> CLAN_TAGS = new HashMap<>(){
        {
            put("#2QJUL08V9", "아카데미");
            put("#2QG0G20RR", "아카데미 2.0");
            put("#2LPP8PUCG", "아카데미 3.0");
            put("#2L209LC8P", "아카데미 4.0");
            //put("#2QV0R0L8R", "아카데미 5.0");
            put("#2GJGRU920", "아카데미 Academe");
        }
    };
    private ClanCapitalAttackerRes(String clanTag, Integer attackCount) {
        this.name = CLAN_TAGS.get(clanTag);
        this.tag = clanTag;
        this.attackCount = attackCount;
    }

    public static ClanCapitalAttackerRes empty(String clanTag) {
        return new ClanCapitalAttackerRes(clanTag, 0);
    }

    public static ClanCapitalAttackerRes create(String clanTag, int attackCount) {
        return new ClanCapitalAttackerRes(clanTag, attackCount);
    }

    public Integer getRemainAttackCount() {
        final Integer AVAILABLE_ATTACK_COUNT = 50;
        return AVAILABLE_ATTACK_COUNT - attackCount;
    }
}
