package open.api.coc.external.coc.clan.domain.clan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanWar {

    private String state;
    private Integer teamSize;
    private Integer attacksPerMember;

    private String startTime;
    private String endTime;

    private WarClan clan;
    private WarClan opponent;

    public void swapWarClan(String clanTag) {
        if(!clan.getTag().equals(clanTag)) {
            WarClan temp = opponent;
            opponent = clan;
            clan = temp;
        }
    }

}
