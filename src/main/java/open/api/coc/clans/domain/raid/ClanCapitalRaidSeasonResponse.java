package open.api.coc.clans.domain.raid;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCapitalRaidSeasonResponse {

    @Schema(description = "캐피탈 상태")
    private String state;

    @Schema(description = "총 공격 가능 수")
    private final Integer totalAttackLimit = 50;

    @Schema(description = "캐피탈 시작 시간")
    private Long startTime;

    @Schema(description = "캐피탈 종료 시간")
    private Long endTime;

    @Schema(description = "캐피탈 참여자 목록")
    private List<ClanCapitalRaidSeasonMemberResponse> members;

}
