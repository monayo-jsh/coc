package open.api.coc.clans.clean.application.competition.model;

public record CompetitionParticipateClanPlayerQuery(

    // 대회 고유키
    Long competitionId,

    // 클랜 태그
    String clanTag

) {
}
