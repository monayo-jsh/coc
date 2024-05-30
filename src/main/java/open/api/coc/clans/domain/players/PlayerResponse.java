package open.api.coc.clans.domain.players;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import open.api.coc.clans.domain.clans.ClanResponse;
import open.api.coc.clans.domain.clans.LabelResponse;
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

    private LabelResponse league;

    private String role;
    private String warPreference;
    private PlayerClanResponse clan;

    private Integer donations;
    private Integer donationsReceived;

    private List<HeroResponse> heroes;
    private List<HeroEquipmentResponse> heroEquipments;

    private List<TroopsResponse> pets;
    private List<TroopsResponse> siegeMachines;
    private List<TroopsResponse> spells;

    @Setter
    private Integer heroTotalLevel;
    @Setter
    private Integer heroTotalMaxLevel;

    @Setter
    private ClanResponse assignedClan;

    private String supportYn;
}
