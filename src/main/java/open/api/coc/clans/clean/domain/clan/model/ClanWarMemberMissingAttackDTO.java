package open.api.coc.clans.clean.domain.clan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.database.entity.common.YnType;

public record ClanWarMemberMissingAttackDTO(

    @Schema(description = "클랜 이름")
    String clanName,

    @Schema(description = "클랜 정렬값")
    Integer clanOrder,

    @Schema(description = "전쟁 유니크키")
    Long warId,

    @Schema(description = "전쟁 유형")
    ClanWarType warType,

    @Schema(description = "전쟁 상태")
    String warState,

    @Schema(description = "전쟁 시작시간")
    LocalDateTime startTime,

    @Schema(description = "전쟁 종료시간")
    LocalDateTime endTime,

    @Schema(description = "플레이어 태그")
    String tag,

    @Schema(description = "플레이어 이름")
    String name,

    @Schema(description = "필수 참여 여부")
    YnType necessaryAttackYn

) {
}
