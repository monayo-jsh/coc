package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class IconUrlResponse {

    @Schema(description = "아주 작은 아이콘 경로")
    private String tiny;

    @Schema(description = "작은 아이콘 경로")
    private String small;

    @Schema(description = "보통 아이콘 경로")
    private String medium;

    @Schema(description = "큰 아이콘 경로")
    private String large;

}
