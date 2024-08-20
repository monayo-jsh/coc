package open.api.coc.clans.domain.ranking;

import io.swagger.v3.oas.annotations.media.Schema;

public record RankingHallOfFameDTO(

    @Schema(description = "태그")
    String tag,

    @Schema(description = "이름")
    String name,

    @Schema(description = "점수")
    Integer score

) {
}
