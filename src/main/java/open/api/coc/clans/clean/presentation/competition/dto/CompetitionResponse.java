package open.api.coc.clans.clean.presentation.competition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class CompetitionResponse {

    @Schema(description = "대회 고유키")
    private Long id;

    @Schema(description = "대회 이름")
    private String name;

    @Schema(description = "대회 시작일")
    private LocalDate startDate;

    @Schema(description = "대회 종료일")
    private LocalDate endDate;

    @Schema(description = "대회 디스코드 URL")
    private String discordUrl;

    @Schema(description = "대회 룰북 URL")
    private String ruleBookUrl;

    @Schema(description = "최대 등록 멤버수")
    private Integer roasterSize;

    @Schema(description = "제한사항")
    private String restrictions;

    @Schema(description = "대회 색상")
    private String bgColor;

    @Schema(description = "메모")
    private String remarks;

}
