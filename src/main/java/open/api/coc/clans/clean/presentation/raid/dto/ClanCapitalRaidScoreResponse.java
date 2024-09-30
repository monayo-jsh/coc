package open.api.coc.clans.clean.presentation.raid.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import open.api.coc.clans.clean.presentation.common.dto.ClanSimpleResponse;

public record ClanCapitalRaidScoreResponse(

    @Schema(description = "플레이어 참여 고유키")
    Long id,

    @Schema(description = "플레이어 태그")
    String tag,

    @Schema(description = "플레이어 이름")
    String name,

    @Schema(description = "진행 클랜")
    ClanSimpleResponse clan,

    @Schema(description = "캐피탈 시작일시")
    long seasonStartDate,

    @Schema(description = "캐피탈 종료일시")
    long seasonEndDate,

    @Schema(description = "진행 공격 수")
    Integer attacks,

    @Schema(description = "캐피탈 획득 점수")
    Integer resourceLooted


) {

    public Integer getClanOrder() {
        return clan.order();
    }

}
