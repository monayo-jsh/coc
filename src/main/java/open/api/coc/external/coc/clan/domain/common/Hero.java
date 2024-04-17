package open.api.coc.external.coc.clan.domain.common;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Hero {

    private String name;
    private String village;
    private Integer level;
    private Integer maxLevel;

    private List<HeroEquipment> equipment;

}
