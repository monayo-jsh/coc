package open.api.coc.clans.clean.presentation.league.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import open.api.coc.clans.domain.clans.IconUrlResponse;

public record LeagueResponse(

    @Schema(description = "리그 고유 ID")
    Integer id,

    @Schema(description = "리그명")
    String name,

    @Schema(description = "아이콘 경로 객체")
    IconUrlResponse iconUrls

) {
}
