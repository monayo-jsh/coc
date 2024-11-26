package open.api.coc.clans.clean.presentation.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record LabelResponse(

    @Schema(description = "고유키")
    Integer id,

    @Schema(description = "이름")
    String name,

    @Schema(description = "아이콘")
    IconUrlResponse iconUrl

) {
}
