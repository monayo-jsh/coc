package open.api.coc.clans.domain.raid;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.players.PlayerClanResponse;

@Getter
@Builder
@AllArgsConstructor
public class RaidScoreResponse {

    @Schema(description = "사용자 태그")
    private String tag;

    @Schema(description = "사용자 이름")
    private String name;

    @Schema(description = "진행 클랜")
    private PlayerClanResponse clan;

    @Schema(description = "캐피탈 시작일시")
    private long seasonStartDate;

    @Schema(description = "캐피탈 종료일시")
    private long seasonEndDate;

    @Schema(description = "진행 공격 수")
    private Integer attacks;

    @Schema(description = "캐피탈 획득 점수")
    private Integer resourceLooted;

}
