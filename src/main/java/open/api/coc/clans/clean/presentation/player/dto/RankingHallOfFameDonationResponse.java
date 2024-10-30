package open.api.coc.clans.clean.presentation.player.dto;

import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.clean.presentation.common.dto.RankingHallOfFameResponse;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
public class RankingHallOfFameDonationResponse extends RankingHallOfFameResponse {

    private final YnType supportYn;

    @Builder
    public RankingHallOfFameDonationResponse(String tag, String name, Integer score,
                                             Integer townHallLevel, YnType supportYn) {
        super(tag, name, score, townHallLevel);
        this.supportYn = supportYn;
    }

}
