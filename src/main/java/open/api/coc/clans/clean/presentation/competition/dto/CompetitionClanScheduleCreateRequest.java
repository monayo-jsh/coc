package open.api.coc.clans.clean.presentation.competition.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CompetitionClanScheduleCreateRequest(

    // 라운드 설명
    @NotEmpty(message = "타이틀을 입력해주세요.")
    @Size(max = 200)
    String name,

    // 라운드 시작일
    @NotNull
    LocalDate startDate,

    // 라운드 종료일
    @NotNull
    LocalDate endDate

) {
}
