package open.api.coc.clans.clean.domain.capital.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanCapitalRaidMemberRankingDTO {

    private String tag; // 플레이어 태그
    private String name; // 플레이어 이름
    private Integer townHallLevel; // 플레이어 이름
    private Integer resourceLooted; // 플레이어 획득 점수

}
