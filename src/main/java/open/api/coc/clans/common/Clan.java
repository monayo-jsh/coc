package open.api.coc.clans.common;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public enum Clan {

    CLAN_ACADEME("#2QJUL08V9", "아카데미", 1),
    CLAN_ACADEME_2("#2QG0G20RR", "아카데미 2.0", 2),
    CLAN_ACADEME_3("#2LPP8PUCG", "아카데미 3.0", 3),
    CLAN_ACADEME_4("#2L209LC8P", "아카데미 4.0", 4),
//    CLAN_ACADEME_5("#2QV0R0L8R", "아카데미 5.0", 5),

    LEAGUE_ACADEME("#2GJGRU920", "Academe", 6);

    private final String tag;
    private final String name;
    private final int order;

    Clan(String tag, String name, int order) {
        this.tag = tag;
        this.name = name;
        this.order = order;

    }

    public static Clan findByTag(String findTag) {
        return Arrays.stream(values())
                     .filter(clan -> Objects.equals(clan.getTag(), findTag))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("클랜태그 일치하는 클랜 정의 찾지 못함"));
    }

    public static List<String> getCapitalClanTagList() {
        return List.of(CLAN_ACADEME.getTag(),
                       CLAN_ACADEME_2.getTag(),
                       CLAN_ACADEME_3.getTag(),
                       CLAN_ACADEME_4.getTag(),
                       LEAGUE_ACADEME.getTag());
    }
}
