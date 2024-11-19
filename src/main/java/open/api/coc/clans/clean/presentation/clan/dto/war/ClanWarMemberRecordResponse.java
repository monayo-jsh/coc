package open.api.coc.clans.clean.presentation.clan.dto.war;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClanWarMemberRecordResponse {

    @Schema(description = "클랜 태그")
    private String clanTag;
    @Schema(description = "클랜 이름")
    private String clanName;
    @Schema(description = "클랜 순서")
    private Integer clanOrder;

    @Schema(description = "태그")
    private String tag;
    @Schema(description = "이름")
    private String name;
    @Schema(description = "타운홀 레벨")
    private Integer townHallLevel;

    @Schema(description = "총 공격수")
    private Long totalAttackCount;
    @Schema(description = "총 파괴율")
    private Integer totalDestructionPercentage;
    @Schema(description = "평균 공격시간")
    private Double avgDuration;

    @Schema(description = "총 획득별")
    private Integer totalStars;
    @Schema(description = "3별 획득수")
    private Integer threeStars;
    @Schema(description = "2별 획득수")
    private Integer twoStars;
    @Schema(description = "1별 획득수")
    private Integer oneStars;
    @Schema(description = "0별 획득수")
    private Integer zeroStars;

}
