package open.api.coc.clans.clean.presentation.raid.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCapitalRaidMemberResponse {

    @Schema(description = "플레이어 참여 고유키")
    private String id;

    @Schema(description = "플레이어 태그")
    private String tag;

    @Schema(description = "플레이어 이름")
    private String name;

    @Schema(description = "현재 진행 공격수")
    private Integer attacks;

    @Schema(description = "기본 공격 가능 수")
    private Integer attackLimit;

    @Schema(description = "보너스 공격 가능 수")
    private Integer bonusAttackLimit;

    @Schema(description = "캐피탈 획득 점수")
    private Integer capitalResourcesLooted;

}
