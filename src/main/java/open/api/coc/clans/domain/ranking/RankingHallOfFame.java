package open.api.coc.clans.domain.ranking;

public interface RankingHallOfFame {

    String getName();
    String getTag();
    Integer getScore();

    String getClanTag();
    String getClanName();
    Integer getDestructionPercentage();
    Integer getDuration();
}
