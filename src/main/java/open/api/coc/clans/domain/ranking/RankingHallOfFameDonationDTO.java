package open.api.coc.clans.domain.ranking;

import lombok.Getter;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
public class RankingHallOfFameDonationDTO extends RankingHallOfFameDTO {

    private final YnType supportYn;

    public RankingHallOfFameDonationDTO(String tag, String name, Integer score, Integer townHallLevel, YnType supportYn) {
        super(tag, name, score, townHallLevel);
        this.supportYn = supportYn;
    }
}
