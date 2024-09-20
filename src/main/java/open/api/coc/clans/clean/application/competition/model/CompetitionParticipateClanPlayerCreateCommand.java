package open.api.coc.clans.clean.application.competition.model;

public record CompetitionParticipateClanPlayerCreateCommand(

    // 대회 고유키
    Long competitionId,

    // 클랜 태그
    String clanTag,

    // 사용자 태그
    String playerTag

) {
}
