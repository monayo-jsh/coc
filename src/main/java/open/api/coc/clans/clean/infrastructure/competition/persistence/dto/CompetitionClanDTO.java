package open.api.coc.clans.clean.infrastructure.competition.persistence.dto;

import org.hibernate.annotations.Comment;

public record CompetitionClanDTO(

    @Comment("대회 참여 클랜 유니크키")
    Long id,

    @Comment("대회 고유키(참조)")
    Long compId,

    @Comment("클랜 태그")
    String clanTag,

    @Comment("클랜 이름")
    String clanName,

    @Comment("대회 참여 상태")
    String status

) {
}
