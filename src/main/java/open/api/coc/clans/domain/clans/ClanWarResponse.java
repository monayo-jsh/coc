package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import open.api.coc.clans.database.entity.clan.ClanWarType;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarResponse {

    @Schema(description = "전쟁 유니크키")
    private Long warId;

    @Schema(description = "태그")
    private String clanTag;

    @Schema(description = "전쟁 유형")
    private ClanWarType type;

    @Schema(description = "이름")
    private String clanName;

    @Schema(description = "전쟁 상태")
    private String state;

    @Schema(description = "전쟁 유형")
    private String battleType;

    @Schema(description = "전쟁 인원")
    private Integer teamSize;

    @Schema(description = "공격 가능수")
    private Integer attacksPerMember;

    @Schema(description = "시작 시간")
    private long startTime;

    @Schema(description = "종료 시간")
    private long endTime;

    @Schema(description = "참여 멤버")
    @Setter
    private List<ClanWarMemberResponse> members;

}
