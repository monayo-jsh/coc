package open.api.coc.clans.clean.presentation.player.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlayerHeroEquipmentResponse {

    @Schema(description = "코드")
    private Integer code;

    @Schema(description = "등급 - normal: 일반, epic: 에픽, unknown: 매핑 필요")
    private String rarity;

    @Schema(description = "이름")
    private String name;

    @Schema(description = "한국 이름")
    private String koreanName;

    @Schema(description = "제공 마을 유형 - home: 본 마을, builderBase: 장인기지")
    private String village;

    @Schema(description = "현재 레벨")
    private Integer level;

    @Schema(description = "최대 레벨")
    private Integer maxLevel;

    // TODO 정말 필요한가? 여기서 응답을 내려주는게 맞는가? 그렇지 않다면 제거할 것.
    @Schema(description = "착용중인 영웅 이름")
    private String heroName;

    // TODO 정말 필요한가? 여기서 응답을 내려주는게 맞는가? 그렇지 않다면 제거할 것.
    @Schema(description = "착용중인 영웅 한글 이름 약자")
    private String heroShortName;

}