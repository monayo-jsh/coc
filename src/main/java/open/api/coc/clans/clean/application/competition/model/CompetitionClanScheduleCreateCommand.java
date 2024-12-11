package open.api.coc.clans.clean.application.competition.model;

import java.time.LocalDate;

public record CompetitionClanScheduleCreateCommand(

    // 대회 고유키
    Long competitionId,

    // 클랜 태그
    String clanTag,

    // 라운드 설명
    String description,

    // 라운드 시작일
    LocalDate startDate,

    // 라운드 종료일
    LocalDate endDate

) {
}
