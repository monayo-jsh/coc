package open.api.coc.external.coc.clan.domain.capital;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalRaidSeasonMember {

    private String tag;
    private String name;
    private Integer attacks;
    private Integer attackLimit;
    private Integer bonusAttackLimit;
    private Integer capitalResourcesLooted;

    public boolean isUnderAttacks() {
        final int ATTACK_COUNT = 6;
        return attacks < ATTACK_COUNT;
    }

}
