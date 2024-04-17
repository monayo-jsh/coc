package open.api.coc.external.coc.clan.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HeroEquipment {

    private String name;
    private String village;

    private Integer level;
    private Integer maxLevel;

}
