package open.api.coc.clans.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarMemberRes {

    private String name;
    private String tag;
    private Integer townHallLevel;
    private Integer mapPosition;
    private List<ClanWarAttackRes> attacks;
    private ClanWarAttackRes bestOpponentAttack;

}
