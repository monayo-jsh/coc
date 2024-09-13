package open.api.coc.clans.clean.domain.competition.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class CompetitionClan {

    // 대회 참가 고유키
    private Long id;
    // 대회 고유키
    private Long compId;
    // 대회 참여 클랜 태그
    private String clanTag;

    public static CompetitionClan createNew(Long compId, String clanTag) {
        return CompetitionClan.builder()
                              .compId(compId)
                              .clanTag(clanTag)
                              .build();
    }
}
