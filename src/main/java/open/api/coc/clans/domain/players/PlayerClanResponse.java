package open.api.coc.clans.domain.players;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.clans.IconUrlResponse;

@Getter
@Builder
@AllArgsConstructor
public class PlayerClanResponse {

    @Schema(description = "클랜 태그")
    private String tag;

    @Schema(description = "클랜 이름")
    private String name;

    @Schema(description = "클랜 레벨")
    private Integer clanLevel;

    @Schema(description = "클랜 순서")
    private Integer order;

    @Schema(description = "클랜 아이콘 객체")
    private IconUrlResponse badgeUrls;

}
