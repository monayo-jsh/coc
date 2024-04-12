package open.api.coc.external.coc.clan.domain.clan;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanWarMember {

    private String name;
    private String tag;
    private Integer townhallLevel;
    private Integer mapPosition;
    private List<ClanWarAttack> attacks;
    private ClanWarAttack bestOpponentAttack;

}
