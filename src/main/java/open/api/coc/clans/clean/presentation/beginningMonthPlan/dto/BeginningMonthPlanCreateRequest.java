package open.api.coc.clans.clean.presentation.beginningMonthPlan.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BeginningMonthPlanCreateRequest(

    @Schema(description = "날짜")
    @NotNull(message = "날짜를 입력해주세요.")
    Long date,

    @Schema(description = "리그전")
    String leagueWar,

    @Schema(description = "클랜전")
    String clanWar,

    @Schema(description = "습격전")
    String capital

) {
}
