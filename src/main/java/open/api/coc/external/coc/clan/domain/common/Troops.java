package open.api.coc.external.coc.clan.domain.common;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Troops {

    private String name;
    private Integer level;
    private Integer maxLevel;
    private String village;

    public boolean isVillageHome() {
        return Objects.equals("home", village);
    }

}
