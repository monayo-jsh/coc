package open.api.coc.clans.clean.presentation.raid.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ClanCapitalRaidResponse {

    @Schema(description = "캐피탈 고유키")
    private String id;

    @Schema(description = "캐피탈 상태")
    private String state;

    @Schema(description = "총 공격 가능 수")
    private final Integer totalAttackLimit = 50;

    @Schema(description = "캐피탈 시작일시")
    private Long startTime;

    @Schema(description = "캐피탈 종료일시")
    private Long endTime;

    @Schema(description = "캐피탈 참여자 목록")
    private List<ClanCapitalRaidMemberResponse> members;

}
