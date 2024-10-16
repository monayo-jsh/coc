package open.api.coc.clans.clean.presentation.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlayerTroopResponse {

    @Schema(description = "유형")
    private String type;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "한글 이름")
    private String koreanName;

    @Schema(description = "현재 레벨")
    private Integer level;

    @Schema(description = "최대 레벨")
    private Integer maxLevel;

    @Schema(description = "제공 마을 유형 - home: 본 마을, builderBase: 장인기지")
    private String village;

    @Schema(description = "정렬 순서")
    private Integer order;

}
