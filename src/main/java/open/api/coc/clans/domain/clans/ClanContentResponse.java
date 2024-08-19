package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClanContentResponse {

    @Schema(description = "클랜 태그")
    private String tag;

    @Schema(description = "클랜전 활성화 여부")
    private String clanWarYn;

    @Schema(description = "리그전 활성화 여부")
    private String clanWarLeagueYn;

    @Schema(description = "캐피탈 활성화 여부")
    private String clanCapitalYn;

    @Schema(description = "병행클랜전 활성화 여부")
    private String clanWarParallelYn;

}
