package open.api.coc.clans.domain.players;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.clans.IconUrlRes;

@Getter
@Builder
@AllArgsConstructor
public class ClanResponse {

    private String tag;
    private String name;
    private Integer clanLevel;

    private IconUrlRes badgeUrls;

}
