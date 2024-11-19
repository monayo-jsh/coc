package open.api.coc.clans.clean.presentation.clan.dto.war;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ClanWarMemberAttackResponse {

    @Schema(description = "전쟁 유니크키")
    private Long warId;

    @Schema(description = "플레이어 태그")
    private String tag;

    @Schema(description = "획득별")
    private Integer stars;

    @Schema(description = "파괴율")
    private Integer destructionPercentage;

    @Schema(description = "공격 시간")
    private Integer duration;

    @Schema(description = "공격 순서")
    private Integer order;

}
