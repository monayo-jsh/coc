package open.api.coc.clans.clean.presentation.clan.dto.war;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.entity.common.YnType;

@Builder
@Getter
@AllArgsConstructor
public class ClanWarMemberMissingAttackResponse {

    @Schema(description = "클랜 이름")
    private String clanName;

    @Schema(description = "클랜 정렬값")
    private Integer clanOrder;

    @Schema(description = "전쟁 유니크키")
    private Long warId;

    @Schema(description = "전쟁 유형")
    private ClanWarType warType;

    @Schema(description = "전쟁 상태")
    private String warState;

    @Schema(description = "전쟁 시작시간")
    private Long startTime;

    @Schema(description = "전쟁 종료시간")
    private Long endTime;

    @Schema(description = "플레이어 태그")
    private String tag;

    @Schema(description = "플레이어 이름")
    private String name;

    @Schema(description = "필수 참여 여부")
    private YnType necessaryAttackYn;

}
