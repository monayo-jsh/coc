package open.api.coc.external.coc.clan.domain.player;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.common.Hero;
import open.api.coc.external.coc.clan.domain.common.HeroEquipment;
import open.api.coc.external.coc.clan.domain.common.Troops;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private String name;
    private String tag;
    private Integer expLevel;

    private Integer townHallLevel;

    private Integer trophies;
    private Integer bestTrophies;

    private Integer attackWins;
    private Integer defenseWins;

    private Integer warStars;

    private List<Hero> heroes;
    private List<HeroEquipment> heroEquipment;

    private List<Troops> troops;

}
