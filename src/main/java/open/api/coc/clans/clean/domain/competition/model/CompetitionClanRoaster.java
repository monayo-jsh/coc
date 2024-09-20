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
public class CompetitionClanRoaster {

    // 대회 참가 고유키
    private Long compClanId;

    // 사용자 태그
    private String playerTag;

    public static CompetitionClanRoaster createNew(Long compClanId, String playerTag) {
        return CompetitionClanRoaster.builder()
                                     .compClanId(compClanId)
                                     .playerTag(playerTag)
                                     .build();
    }

}
