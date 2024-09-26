package open.api.coc.clans.clean.presentation.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingHallOfFameResponse {

    @Schema(description = "태그")
    private final String tag;

    @Schema(description = "이름")
    private final String name;

    @Schema(description = "점수")
    private final Integer score;

}
