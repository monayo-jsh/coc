package open.api.coc.clans.clean.infrastructure.common.external.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
