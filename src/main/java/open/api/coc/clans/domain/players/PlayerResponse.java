package open.api.coc.clans.domain.players;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.common.HeroEquipmentResponse;
import open.api.coc.clans.domain.common.HeroResponse;
import open.api.coc.clans.domain.common.TroopsResponse;

@Getter
@Builder
@AllArgsConstructor
public class PlayerResponse {

    private String name;
    private String tag;
    private Integer expLevel;

    private Integer townHallLevel;

    private Integer trophies;
    private Integer bestTrophies;

    private Integer attackWins;
    private Integer defenseWins;

    private Integer warStars;

    private List<HeroResponse> heroes;
    private List<HeroEquipmentResponse> heroEquipments;

    private List<TroopsResponse> pets;

}
