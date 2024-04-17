package open.api.coc.clans.domain.clans;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

@Getter
public class ClanMemberListRes {

    private final List<ClanMemberRes> members;

    private ClanMemberListRes(List<ClanMemberRes> members) {
        this.members = members;
    }

    public static ClanMemberListRes empty() {
        return new ClanMemberListRes(Collections.emptyList());
    }

    public static ClanMemberListRes create(List<ClanMemberRes> members) {
        return new ClanMemberListRes(members);
    }
}
