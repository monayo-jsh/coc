package open.api.coc.clans.clean.presentation.beginningMonthPlan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record BeginningMonthResponse(

    @Schema(description = "월초 일정 고유키")
    Long id,

    @Schema(description = "월초 날짜")
    Long month,

    @Schema(description = "월초 일정 목록")
    List<BeginningMonthPlanResponse> plans

) {
}
