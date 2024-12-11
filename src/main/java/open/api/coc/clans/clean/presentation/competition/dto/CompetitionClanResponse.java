package open.api.coc.clans.clean.presentation.competition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class CompetitionClanResponse {

    @Schema(description = "대회 참여 고유키")
    private Long id;

    @Schema(description = "대회 고유 키")
    private String compId;

    @Schema(description = "대회 참여 상태")
    private String status;

    @Schema(description = "클랜 태그")
    private String tag;

    @Schema(description = "클랜 이름")
    private String name;

}
