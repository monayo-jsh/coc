package open.api.coc.clans.clean.infrastructure.player.external.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class PlayerLeagueResponse {

    private Integer id; // 고유키
    private String name; // 이름
    private IconResponse iconUrls; // 아이콘

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Builder
    public static class IconResponse {

        private String tiny; // 아주 작은 아이콘 경로
        private String small; // 작은 아이콘 경로
        private String medium; // 보통 아이콘 경로
        private String large; // 큰 아이콘 경로

    }

}
