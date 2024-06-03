package open.api.coc.external.coc.clan.domain.clan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.external.coc.clan.domain.common.IconUrl;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarLeague {

    private String tag;
    private String name;
    private Integer clanLevel;
    private IconUrl badgeUrls;

    private List<ClanMember> members;

}
