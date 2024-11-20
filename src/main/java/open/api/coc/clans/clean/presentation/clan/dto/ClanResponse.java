package open.api.coc.clans.clean.presentation.clan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import open.api.coc.clans.clean.presentation.common.dto.LeagueResponse;

public record ClanResponse(

    @Schema(description = "클랜 태그")
    String tag,

    @Schema(description = "클랜 이름")
    String name,

    @Schema(description = "리그전 리그 정보")
    LeagueResponse warLeague,

    @Schema(description = "캐피탈 리그 정보")
    LeagueResponse capitalLeague,

    @Schema(description = "캐피탈 트로피 점수")
    Integer clanCapitalPoints,

    @Schema(description = "캐피탈 정보")
    ClanCapitalResponse clanCapital,

    @Schema(description = "맥스쟁 유무")
    Boolean isMaxWar,

    @Schema(description = "전쟁 선점 방식")
    Boolean isOccupy,

    @Schema(description = "리그 선점 방식")
    Boolean isLeagueOccupy,

    @Schema(description = "전쟁 설명")
    String warDescription,

    @Schema(description = "클랜 정렬 순서")
    Integer order,

    @Schema(description = "클랜 컨텐츠 활성화 정보")
    ClanContentResponse clanContent
    
) {
}
