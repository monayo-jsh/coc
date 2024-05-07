package open.api.coc.clans.common;

import static open.api.coc.clans.common.exception.handler.ExceptionHandler.createNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import open.api.coc.external.coc.clan.domain.clan.Clan;

@Getter
public enum AcademeClan {

    CLAN_ACADEME("#2QJUL08V9", "아카데미", 1),
    CLAN_ACADEME_2("#2QG0G20RR", "아카데미 2.0", 2),
    CLAN_ACADEME_3("#2LPP8PUCG", "아카데미 3.0", 3),
    CLAN_ACADEME_4("#2L209LC8P", "아카데미 4.0", 4),
    CLAN_ACADEME_5("#2QV0R0L8R", "아카데미 5.0", 5),

    ACADEME_ENG("#2GJGRU920", "Academe", 6),

    LEAGUE_TEAM_ACADEME("#2GPGU92Q2", "TEAM Academe", 7),
    LEAGUE_TEAM_BCADEME("#2GY9YL0J9", "TEAM Bcademe", 8),
    LEAGUE_TEAM_CCADEME("#2QJLPVPQU", "TEAM Ccademe", 9),

    CLAN_WAR_LEAGUE("#229V992R8", "클랜전리그", 20),
    CLAN_SIX_DRAGON("#2G9GU9PLU", "육룡이 나르샤", 21),
    CLAN_BAR_RICE_CAKE_HONEY("#2PLJJLY89", "가래떡에 꿀", 22),
    CLAN_BAR_RICE_CAKE_SUGAR("#2PUPJ09VP", "가래떡에 설탕", 23),

    CLAN_ACADEME_SHELTER("#2LJ0U02YJ", "아카데미 쉼터", 99);

    private final String tag;
    private final String name;
    private final int order;

    AcademeClan(String tag, String name, int order) {
        this.tag = tag;
        this.name = name;
        this.order = order;

    }

    public static AcademeClan findByTag(String findTag) {
        return Arrays.stream(values())
                     .filter(clan -> Objects.equals(clan.getTag(), findTag))
                     .findFirst()
                     .orElseThrow(() -> createNotFoundException("클랜태그 일치하는 클랜 정의 찾지 못함"));
    }

    public static List<AcademeClan> getClanList() {
        return List.of(
            CLAN_ACADEME,
            CLAN_ACADEME_2,
            CLAN_ACADEME_3,
            CLAN_ACADEME_4,
            CLAN_ACADEME_5,
            ACADEME_ENG,
            LEAGUE_TEAM_ACADEME,
            LEAGUE_TEAM_BCADEME,
            CLAN_WAR_LEAGUE,
            CLAN_SIX_DRAGON,
            CLAN_BAR_RICE_CAKE_HONEY,
            CLAN_BAR_RICE_CAKE_SUGAR,
            CLAN_ACADEME_SHELTER
        );
    }

    public static List<AcademeClan> getClanWarList() {
        return List.of(
            CLAN_ACADEME,
            CLAN_ACADEME_2,
            CLAN_ACADEME_3,
            CLAN_ACADEME_4,
            CLAN_ACADEME_5,
            ACADEME_ENG
        );
    }

    public static List<AcademeClan> getClanWarParallelList() {
        return List.of(
            LEAGUE_TEAM_BCADEME,
            LEAGUE_TEAM_CCADEME,
            CLAN_ACADEME_SHELTER
        );
    }

    public static List<AcademeClan> getClanCapitalList() {
        return List.of(
            CLAN_ACADEME,
            CLAN_ACADEME_2,
            CLAN_ACADEME_3,
            CLAN_ACADEME_4,
            ACADEME_ENG
        );
    }


    public static List<String> getCapitalClanTagList() {
        return List.of(
            CLAN_ACADEME.getTag(),
            CLAN_ACADEME_2.getTag(),
            CLAN_ACADEME_3.getTag(),
            CLAN_ACADEME_4.getTag(),
            ACADEME_ENG.getTag()
        );
    }

    public static List<AcademeClan> getLeagueClanList() {
        return List.of(
                CLAN_ACADEME,
                CLAN_ACADEME_2,
                CLAN_ACADEME_3,
                CLAN_ACADEME_4,
                CLAN_ACADEME_5,
                ACADEME_ENG,
                LEAGUE_TEAM_ACADEME,
                CLAN_WAR_LEAGUE,
                CLAN_SIX_DRAGON,
                CLAN_BAR_RICE_CAKE_HONEY,
                CLAN_BAR_RICE_CAKE_SUGAR
        );
    }

    public static List<String> getClanWarLeagueTagList() {
        return List.of(
                CLAN_ACADEME.getTag(),
                CLAN_ACADEME_2.getTag(),
                CLAN_ACADEME_3.getTag(),
                CLAN_ACADEME_4.getTag(),
                CLAN_ACADEME_5.getTag(),
                ACADEME_ENG.getTag(),
                LEAGUE_TEAM_ACADEME.getTag(),
                CLAN_WAR_LEAGUE.getTag(),
                CLAN_SIX_DRAGON.getTag(),
                CLAN_BAR_RICE_CAKE_HONEY.getTag(),
                CLAN_BAR_RICE_CAKE_SUGAR.getTag()
        );
    }
}
