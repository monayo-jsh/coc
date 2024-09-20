package open.api.coc.clans.clean.application.competition.model;

public record CompetitionClanScheduleDeleteCommand(

    // 대회 고유키
    Long competitionId,

    // 클랜 태그
    String clanTag,

    // 대회 참여 클랜 일정 고유키
    Long clanScheduleId

) {
}
