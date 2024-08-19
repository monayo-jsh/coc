package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanMemberResponse {

    @Schema(description = "클랜원 태그")
    private String tag;

    @Schema(description = "클랜원 이름")
    private String name;

    @Schema(description = "클랜원 직위")
    private String role;

    @Schema(description = "클랜원 타운홀 레벨")
    private Integer townHallLevel;

    @Schema(description = "클랜원 EXP 레벨")
    private Integer expLevel;

    @Schema(description = "클랜원 리그 정보")
    private LabelResponse league;

    @Schema(description = "클랜원 트로피 수")
    private Integer trophies;

    @Schema(description = "클랜원 클랜 내 순위")
    private Integer clanRank;

    @Schema(description = "클랜원 클랜 내 이전 순위")
    private Integer previousClanRank;

    @Schema(description = "클랜원 클랜 지원수")
    private Integer donations;

    @Schema(description = "클랜원 클랜 지원 받은수")
    private Integer donationsReceived;

}
