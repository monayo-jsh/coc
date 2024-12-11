package open.api.coc.clans.clean.application.competition.model;

public record CompetitionParticipateCreateCommand(

    // 대회 고유키
    Long competitionId,

    // 클랜 태그
    String clanTag

) {
}
