package open.api.coc.clans.clean.domain.clan.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanAssignedPlayer {

    // 배정월
    private String assignedDate;

    // 플레이어 태그
    private String playerTag;

    // 클랜 태그
    private String clanTag;

}
