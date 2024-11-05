package open.api.coc.clans.domain.clans;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCurrentWarResponse {

    private String state;
    private String battleModifier;
    private Integer teamSize;
    private Integer attacksPerMember;

    private long startTime;
    private long endTime;

    private ClanWarRes clan;
    private ClanWarRes opponent;

    public Integer getTeamSize() {
        if (Objects.isNull(teamSize)) return 0;
        return teamSize;
    }

    public Integer getAttacksPerMember() {
        if (Objects.isNull(attacksPerMember)) return 0;
        return attacksPerMember;
    }
}
