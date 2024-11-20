package open.api.coc.clans.clean.presentation.clan.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClanContentResponse(

    @Schema(description = "태그")
    String tag,

    @Schema(description = "클랜전 활성화 여부")
    String clanWarYn,

    @Schema(description = "리그전 활성화 여부")
    String clanWarLeagueYn,

    @Schema(description = "캐피탈 활성화 여부")
    String clanCapitalYn,

    @Schema(description = "병행클랜전 활성화 여부")
    String clanWarParallelYn

) {
}
