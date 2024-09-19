package open.api.coc.clans.clean.presentation.competition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record CompetitionResponse(

    @Schema(description = "대회 고유키")
    Long id,

    @Schema(description = "대회 이름")
    String name,

    @Schema(description = "대회 시작일")
    LocalDate startDate,

    @Schema(description = "대회 종료일")
    LocalDate endDate,

    @Schema(description = "대회 디스코드 URL")
    String discordUrl,

    @Schema(description = "대회 룰북 URL")
    String ruleBookUrl,

    @Schema(description = "최대 등록 멤버수")
    Integer roasterSize,

    @Schema(description = "제한사항")
    String restrictions,

    @Schema(description = "대회 색상")
    String bgColor,

    @Schema(description = "메모")
    String remarks

) {
}
