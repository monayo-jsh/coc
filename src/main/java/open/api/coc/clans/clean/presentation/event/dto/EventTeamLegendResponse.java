package open.api.coc.clans.clean.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public record EventTeamLegendResponse(

    @Schema(description = "이벤트 고유키")
    Long id,

    @Schema(description = "이벤트 이름")
    String name,

    @Schema(description = "이벤트 유형 - TEAM_LEGEND: 팀 전설내기")
    String type,

    @Schema(description = "이벤트 시작일시")
    LocalDateTime startDate,

    @Schema(description = "이벤트 종료일시")
    LocalDateTime endDate,

    @Schema(description = "이벤트 진행 상태 - READY: 준비, ING: 진행중, FINISH: 종료")
    String status,

    @Schema(description = "팀 목록")
    List<EventTeamResponse> teams

) {

}
