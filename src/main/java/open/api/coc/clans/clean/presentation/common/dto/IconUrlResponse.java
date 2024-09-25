package open.api.coc.clans.clean.presentation.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record IconUrlResponse(

    @Schema(description = "아주 작은 아이콘 경로")
    String tiny,

    @Schema(description = "작은 아이콘 경로")
    String small,

    @Schema(description = "보통 아이콘 경로")
    String medium,

    @Schema(description = "큰 아이콘 경로")
    String large

){

}
