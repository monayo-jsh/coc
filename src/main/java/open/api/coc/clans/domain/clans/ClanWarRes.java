package open.api.coc.clans.domain.clans;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarRes {

    private String name;
    private String tag;
    private IconUrlRes badge;

    private Integer attacks;
    private Integer stars;
    private Float destructionPercentage;

    private List<ClanWarMemberRes> members;

}
