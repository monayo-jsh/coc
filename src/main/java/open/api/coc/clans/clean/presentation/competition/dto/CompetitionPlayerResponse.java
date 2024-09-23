package open.api.coc.clans.clean.presentation.competition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class CompetitionPlayerResponse {

    @Schema(description = "플레이어 태그")
    private String tag;

    @Schema(description = "플레이어 이름")
    private String name;

    @Schema(description = "플레이어 닉네임")
    private String nickname;

    @Schema(description = "주조합")
    private String oneTroop;

    @Schema(description = "서브조합")
    private String twoTroop;

    @Schema(description = "서브조합")
    private String threeTroop;

    @Schema(description = "소속클랜")
    private String clanTag;

}
