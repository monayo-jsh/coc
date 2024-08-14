package open.api.coc.clans.domain.clans;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LabelResponse {

    @Schema(description = "리그 고유 ID")
    private Integer id;

    @Schema(description = "리그명")
    private String name;

    @Schema(description = "아이콘 경로 객체")
    private IconUrlResponse iconUrls;

}
