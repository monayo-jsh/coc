package open.api.coc.clans.domain.ranking;

import io.swagger.v3.oas.annotations.media.Schema;

public interface RankingHallOfFame {

    @Schema(description = "이름")
    String getName();

    @Schema(description = "태그")
    String getTag();

    @Schema(description = "점수")
    Integer getScore();

}
