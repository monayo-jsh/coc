package open.api.coc.clans.clean.application.competition.model;

import java.time.LocalDate;

public record CompetitionUpdateCommand(

    // 대회 고유키
    Long id,

    // 대회 이름
    String name,

    // 대회 시작일
    LocalDate startDate,

    // 대회 종료일
    LocalDate endDate,

    // 대회 디스코드 URL
    String discordUrl,

    // 대회 룰북 URL
    String ruleBookUrl,

    // 최대 등록 멤버수
    Integer roasterSize,

    // 제한사항
    String restrictions,

    // 메모
    String remarks

) {
}
