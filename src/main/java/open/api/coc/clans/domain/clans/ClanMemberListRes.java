package open.api.coc.clans.domain.clans;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClanMemberListRes {

    private String clanTag;
    private final List<ClanMemberResponse> members;

    private ClanMemberListRes(List<ClanMemberResponse> members) {
        this.members = members;
    }

    public static ClanMemberListRes empty() {
        return new ClanMemberListRes(Collections.emptyList());
    }

    public static ClanMemberListRes create(List<ClanMemberResponse> members) {
        return new ClanMemberListRes(members);
    }
}
