package open.api.coc.clans.domain.clans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCurrentWarRes {

    private String state;
    private Integer teamSize;
    private Integer attacksPerMember;

    private long startTime;
    private long endTime;

    private ClanWarRes clan;
    private ClanWarRes opponent;

}
