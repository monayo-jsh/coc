package open.api.coc.clans.clean.presentation.clan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import open.api.coc.clans.clean.presentation.common.dto.LabelResponse;

public record ClanMemberResponse(

    @Schema(description = "태그")
    String tag,

    @Schema(description = "이름")
    String name,

    @Schema(description = "직위")
    String role,

    @Schema(description = "타운홀 레벨")
    Integer townHallLevel,

    @Schema(description = "EXP 레벨")
    Integer expLevel,

    @Schema(description = "트로피 점시")
    Integer trophies,

    @Schema(description = "클랜 내 순위")
    Integer clanRank,

    @Schema(description = "클랜 내 이전 순위")
    Integer previousClanRank,

    @Schema(description = "지원한 수")
    Integer donations,

    @Schema(description = "지원받은 수")
    Integer donationsReceived,

    @Schema(description = "리그 정보")
    LabelResponse league

) {

}
