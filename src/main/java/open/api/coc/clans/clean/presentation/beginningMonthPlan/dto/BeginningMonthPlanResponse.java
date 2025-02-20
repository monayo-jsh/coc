package open.api.coc.clans.clean.presentation.beginningMonthPlan.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public record BeginningMonthPlanResponse(

    @Schema(description = "날짜")
    Long date,

    @Schema(description = "리그전 설명")
    String leagueWar,

    @Schema(description = "클랜전 설명")
    String clanWar,

    @Schema(description = "습격전 설명")
    String capital

) {
}
