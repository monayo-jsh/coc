package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClanMemberListRes {

    @Schema(description = "클랜 태그")
    private String clanTag;

    @Schema(description = "클랜에 가입중인 플레이어 목록")
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
