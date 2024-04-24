package open.api.coc.external.coc.clan.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerClan {

    private String tag;
    private String name;
    private Integer clanLevel;

    private IconUrl badgeUrls;

}
