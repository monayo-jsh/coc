package open.api.coc.clans.clean.presentation.clan.dto.war;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
@Builder
@AllArgsConstructor
public class ClanWarMemberResponse {

    @Schema(description = "전쟁 유니크키")
    private Long warId;

    @Schema(description = "맵 포지션")
    private Integer mapPosition;

    @Schema(description = "플레이어 태그")
    private String tag;

    @Schema(description = "플레이어 이름")
    private String name;

    @Schema(description = "필수 참여 여부")
    private YnType necessaryAttackYn;

    @Builder.Default
    @Schema(description = "전쟁 공격 기록")
    private List<ClanWarMemberAttackResponse> attacks = new ArrayList<>();

}
