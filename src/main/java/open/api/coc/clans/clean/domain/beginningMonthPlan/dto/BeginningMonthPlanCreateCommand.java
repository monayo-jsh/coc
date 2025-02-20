package open.api.coc.clans.clean.domain.beginningMonthPlan.dto;

import java.time.LocalDate;

public record BeginningMonthPlanCreateCommand(

    // 일정 날짜
    LocalDate date,

    // 리그전 설명
    String leagueWarDesc,

    // 클랜전 설명
    String clanWarDesc,

    // 습격전 설명
    String capitalDesc

) {
}
