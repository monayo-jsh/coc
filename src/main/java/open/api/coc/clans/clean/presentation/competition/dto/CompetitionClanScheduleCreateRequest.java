package open.api.coc.clans.clean.presentation.competition.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CompetitionClanScheduleCreateRequest(

    // 라운드 설명
    @NotNull
    String description,

    // 라운드 시작일
    @NotNull
    LocalDate startDate,

    // 라운드 종료일
    @NotNull
    LocalDate endDate

) {
}
