package open.api.coc.clans.clean.presentation.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LeagueResponse(

    @Schema(description = "리그 이름")
    String name

) {
}
