package open.api.coc.clans.clean.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record EventTeamRankResponse(

    @Schema(description = "순위 기준일")
    LocalDate rankingDate,

    @Schema(description = "팀 고유키")
    Long teamId,

    @Schema(description = "팀 이름")
    String teamName,

    @Schema(description = "팀 총 트로피")
    Integer Trophies,

    @Schema(description = "기준일 순위")
    Integer dailyRank

) {
}
