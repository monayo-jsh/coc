package open.api.coc.clans.clean.domain.clan.model;

import java.util.Map;
import java.util.Objects;

public record ClanWarMemberRecordDTO(

    String clanTag,
    String clanName,
    Integer clanOrder,

    String tag,
    String name,
    Integer townHallLevel,

    Long attackCount,
    Integer totalDestructionPercentage,
    Double avgDuration,

    Integer totalStars,
    Integer threeStars,
    Integer twoStars,
    Integer oneStars,
    Integer zeroStars

) {

    public boolean isAllRoundDestroy(Map<String, Integer> leagueWarRoundMap) {
        if (leagueWarRoundMap == null || leagueWarRoundMap.isEmpty()) { return false; }
        Integer leagueWarRoundCount = leagueWarRoundMap.get(this.clanTag);
        Integer totalStarCount = leagueWarRoundCount * 3;

        return Objects.equals(this.totalStars, totalStarCount);
    }
}
