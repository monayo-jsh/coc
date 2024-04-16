package open.api.coc.external.coc.clan.domain.clan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WarClan {

    private String name;
    private String tag;
    private IconUrl badgeUrls;

    private Integer attacks;
    private Integer stars;
    private Float destructionPercentage;

    private List<ClanWarMember> members;

}
