package open.api.coc.clans.clean.presentation.clan.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import open.api.coc.clans.clean.presentation.common.dto.IconUrlResponse;
import open.api.coc.clans.clean.presentation.common.dto.LabelResponse;
import open.api.coc.clans.clean.presentation.common.dto.LeagueResponse;

public record ClanDetailResponse(

    @Schema(description = "태그")
    String tag,

    @Schema(description = "이름")
    String name,

    @Schema(description = "레벨")
    Integer level,

    @Schema(description = "트로피 점수")
    Integer points,

    @Schema(description = "설명")
    String description,

    @Schema(description = "가입 유형")
    String joinType,

    @Schema(description = "맥스쟁 유무")
    Boolean isMaxWar,

    @Schema(description = "전쟁 선점 방식")
    Boolean isOccupy,

    @Schema(description = "리그 선점 방식")
    Boolean isLeagueOccupy,

    @Schema(description = "전쟁 설명")
    String warDescription,

    @Schema(description = "정렬 순서")
    Integer order,

    @Schema(description = "뱃지 아이콘")
    IconUrlResponse badgeUrl,

    @Schema(description = "리그전 리그 정보")
    LeagueResponse warLeague,

    @Schema(description = "캐피탈 리그 정보")
    LeagueResponse capitalLeague,

    @Schema(description = "캐피탈 트로피 점수")
    Integer clanCapitalPoints,

    @Schema(description = "캐피탈 정보")
    ClanCapitalResponse clanCapital,

    @Schema(description = "클랜 컨텐츠 활성화 정보")
    ClanContentResponse clanContent,

    @Schema(description = "라벨 목록")
    List<LabelResponse> labels,

    @Schema(description = "클랜원 목록")
    List<ClanMemberResponse> members

) {
}
