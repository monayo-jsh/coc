package open.api.coc.clans.clean.domain.player.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PlayerLegendRecordTargetDTO(

    @Schema(description = "플레이어 태그")
    String tag,

    @Schema(description = "플레이어 이름")
    String name

) {
}
