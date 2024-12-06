package open.api.coc.clans.clean.presentation.clan.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ClanCapitalResponse(

    @Schema(description = "캐피탈 홀 레벨")
    Integer capitalHallLevel,

    @Schema(description = "캐피탈 티어")
    Integer tier

) {

}
