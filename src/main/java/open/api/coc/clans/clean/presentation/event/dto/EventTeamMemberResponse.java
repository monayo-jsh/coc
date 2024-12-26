package open.api.coc.clans.clean.presentation.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record EventTeamMemberResponse(

    @Schema(description = "팀 고유키")
    Long teamId,

    @Schema(description = "플레이어 태그")
    String tag,

    @Schema(description = "플레이어 이름")
    String name,

    @Schema(description = "현재 트로피")
    Integer trophies,

    @Schema(description = "현재 타운홀 레벨")
    Integer townhallLevel,

    @Schema(description = "최종 수정일시")
    LocalDateTime updatedAt

) {
}
