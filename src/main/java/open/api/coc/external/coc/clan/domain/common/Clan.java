package open.api.coc.external.coc.clan.domain.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.clan.IconUrl;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Clan {

    private String tag;
    private String name;
    private Integer clanLevel;

    private IconUrl badgeUrls;

}
