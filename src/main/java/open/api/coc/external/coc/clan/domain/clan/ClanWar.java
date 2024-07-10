package open.api.coc.external.coc.clan.domain.clan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanWar {

    @Setter
    private String warTag; // 현재는 리그전의 경우 라운드 별 전쟁태그를 획득할 수 있음

    private String state;
    private Integer teamSize;
    private Integer attacksPerMember = 1; //default

    @Setter
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
    public boolean isInWar() {
        return "inWar".equals(state);
    }

    @JsonIgnore
    public boolean isNotNotInWar() {
        return !isNotInWar();
    }

    @JsonIgnore
    public boolean isNotInWar() {
        return "notInWar".equals(state);
    }

    @JsonIgnore
    public boolean isContainClanTag(String clanTag) {
        if (Objects.equals(clanTag, this.clan.getTag())) return true;
        if (Objects.equals(clanTag, this.opponent.getTag())) return true;
        return false;
    }
}
