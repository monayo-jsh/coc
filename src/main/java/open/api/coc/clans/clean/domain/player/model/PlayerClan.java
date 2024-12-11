package open.api.coc.clans.clean.domain.player.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.common.model.IconUrl;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerClan {

    private String tag; // 클랜 태그
    private String name; // 클랜 이름
    private Integer clanLevel; // 클랜 레벨
    private Integer order; // 클랜 정렬 순서

    private IconUrl badgeUrls; // 클랜 아이콘

}
