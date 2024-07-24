package open.api.coc.clans.domain.ranking;

public interface RankingHallOfFameForClanWar extends RankingHallOfFame {

    String getClanTag();
    String getClanName();
    Integer getTotalDestructionPercentage();
    Integer getAvgDuration();

    Integer getAttackCount();
    Integer getTotalStars();
    Integer getThreeStars();
    Integer getTwoStars();
    Integer getOneStars();
    Integer getZeroStars();
}
