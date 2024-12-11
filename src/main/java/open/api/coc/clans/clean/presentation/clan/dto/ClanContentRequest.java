package open.api.coc.clans.clean.presentation.clan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record ClanContentRequest(

    @Schema(description = "클랜전 활성화 여부")
    @Pattern(regexp = "[YN]")
    String clanWarYn,

    @Schema(description = "리그전 활성화 여부")
    @Pattern(regexp = "[YN]")
    String clanWarLeagueYn,

    @Schema(description = "캐피탈 활성화 여부")
    @Pattern(regexp = "[YN]")
    String clanCapitalYn,

    @Schema(description = "병행클랜전 활성화 여부")
    @Pattern(regexp = "[YN]")
    String clanWarParallelYn
    
) {
}
