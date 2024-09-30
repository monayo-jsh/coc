package open.api.coc.clans.clean.presentation.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClanSimpleResponse(

    @Schema(description = "태그")
    String tag,

    @Schema(description = "이름")
    String name,

    @Schema(description = "레벨")
    Integer clanLevel,

    @Schema(description = "정렬 순서")
    Integer order,

    @Schema(description = "아이콘 객체")
    IconUrlResponse badgeUrls

) {

}
