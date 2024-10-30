package open.api.coc.clans.clean.presentation.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlayerHeroResponse {

    @Schema(description = "고유코드")
    private Integer code;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "한국이름")
    private String koreanName;

    @Schema(description = "제공 마을 유형 - home: 본 마을, builderBase: 장인기지")
    private String village;

    @Schema(description = "현재 레벨")
    private Integer level;

    @Schema(description = "최대 레벨")
    private Integer maxLevel;

    @Schema(description = "현재 착용 장비 목록")
    private List<PlayerHeroEquipmentResponse> equipments;

}