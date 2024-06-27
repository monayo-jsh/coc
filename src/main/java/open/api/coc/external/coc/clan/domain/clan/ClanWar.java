package open.api.coc.external.coc.clan.domain.clan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanWar {

    private String state;
    private Integer teamSize;
    private Integer attacksPerMember = 1; //default

    private String battleModifier;

    private String preparationStartTime;
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

    @JsonIgnore
    public boolean isWarEnded() {
        return "warEnded".equals(state);
    }

    @JsonIgnore
    public boolean isNotNotInWar() {
        return !"notInWar".equals(state);
    }
}
