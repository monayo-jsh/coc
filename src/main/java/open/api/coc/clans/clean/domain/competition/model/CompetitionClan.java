package open.api.coc.clans.clean.domain.competition.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.competition.exception.CompetitionClanRoasterAlreadyExistsException;
import open.api.coc.clans.clean.domain.competition.service.CompetitionParticipateClanService;

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
    private String tag;
    // 대회 참여 클랜 이름
    private String name;
    // 대회 참여 상태
    private String status;

    // 대회 참여 클랜 등록 멤버
    @Builder.Default
    private List<String> playerTags = new ArrayList<>();

    public static CompetitionClan createNew(Long compId, String clanTag) {
        return CompetitionClan.builder()
                              .compId(compId)
                              .tag(clanTag)
                              .build();
    }

    public boolean isEqualsClanTag(String clanTag) {
        return Objects.equals(tag, clanTag);
    }

    public void loadRoaster(CompetitionParticipateClanService competitionParticipateClanService) {
        List<CompetitionClanRoaster> roasters = competitionParticipateClanService.findAll(this.id);
        this.playerTags.clear();
        this.playerTags.addAll(roasters.stream().map(CompetitionClanRoaster::getPlayerTag).toList());
    }

    public void validateAlreadyRegistered(String playerTag) {
        if (isRegistered(playerTag)) {
            throw new CompetitionClanRoasterAlreadyExistsException(playerTag);
        }
    }

    private boolean isRegistered(String targetPlayerTag) {
        return this.playerTags.stream().anyMatch(playerTag -> Objects.equals(playerTag, targetPlayerTag));
    }
}
