package open.api.coc.clans.clean.presentation.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import open.api.coc.clans.database.entity.common.YnType;

public record RankingHallOfFameDonationResponse(@Schema(description = "태그") String tag,
                                                @Schema(description = "이름") String name,
                                                @Schema(description = "타운홀 레벨") Integer townHallLevel,
                                                @Schema(description = "지원계정 여부") YnType supportYn,
                                                @Schema(description = "유닛 지원수") Integer troopCount,
                                                @Schema(description = "마법 지원수") Integer spellCount,
                                                @Schema(description = "시즈머신 지원수") Integer siegeCount) {

}
