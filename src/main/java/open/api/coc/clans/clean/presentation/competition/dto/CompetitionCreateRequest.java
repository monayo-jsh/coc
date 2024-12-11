package open.api.coc.clans.clean.presentation.competition.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CompetitionCreateRequest(

    // 대회 이름
    @NotEmpty(message = "대회 이름을 입력해주세요.")
    @Size(max = 30)
    String name,

    // 대회 시작일
    @NotNull(message = "대회 시작 일정을 입력해주세요.")
    LocalDate startDate,

    // 대회 종료일
    @NotNull(message = "대회 종료 일정을 입력해주세요.")
    LocalDate endDate,

    // 대회 디스코드 URL
    @Size(max = 500)
    String discordUrl,

    // 대회 룰북 URL
    @Size(max = 500)
    String ruleBookUrl,

    // 최대 등록 멤버수
    Integer roasterSize,

    // 제한사항
    @Size(max = 500)
    String restrictions,

    // 대회 색상
    @Size(max = 20)
    String bgColor,

    // 메모
    @Size(max = 1000)
    String remarks

) {
}
