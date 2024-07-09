package open.api.coc.clans.domain.clans;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarMemberResponse {

    private Long warId;

    private Integer mapPosition;

    private String tag;

    private String name;

    @Builder.Default
    private List<ClanWarMemberAttackResponse> attacks = new ArrayList<>();

}
