package open.api.coc.clans.clean.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record EventTeamResponse(

    @Schema(description = "팀 고유키")
    Long id,

    @Schema(description = "팀 이름")
    String name,

    @Schema(description = "팀 색상")
    String color,

    @Schema(description = "팀 트로피")
    Integer trophies,

    @Schema(description = "팀원 목록")
    List<EventTeamMemberResponse> members

) {
}
