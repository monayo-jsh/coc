package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LeagueWarMember {
    private String tag;
    private String name;
    private Integer townhallLevel;
    private Integer mapPosition;
    private Integer oppoentAttacks;
}
