package open.api.coc.clans.clean.presentation.clan.dto.war;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClanWarParticipationStatusRecordResponse {

    @Schema(description = "플레이어 태그")
    private String playerTag;
    @Schema(description = "플레이어 이름")
    private String playerName;

    @Schema(description = "클랜전 참전수")
    private String clanWarCount;
    @Schema(description = "병행클랜전 참전수")
    private String parallelWarCount;
    @Schema(description = "리그전 참전수")
    private Integer leagueWarCount;

    @Schema(description = "총 참전수")
    private Long totalParticipationCount;

}
