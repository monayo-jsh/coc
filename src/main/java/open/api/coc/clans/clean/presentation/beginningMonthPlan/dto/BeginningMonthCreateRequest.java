package open.api.coc.clans.clean.presentation.beginningMonthPlan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record BeginningMonthCreateRequest(

    @Schema(description = "등록월")
    @NotNull(message = "등록월을 입력해주세요.")
    Long month,

    @Schema(description = "일정 목록")
    @NotEmpty(message = "일정 목록을 입력해주세요.")
    @Size(min = 1)
    List<BeginningMonthPlanCreateRequest> plans

) {
}
