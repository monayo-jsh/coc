package open.api.coc.clans.clean.domain.clan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanContent {

    @Schema(description = "클랜 태그")
    private String tag;

    @Schema(description = "클랜전 활성화 여부")
    private YnType clanWarYn;

    @Schema(description = "리그전 활성화 여부")
    private YnType clanWarLeagueYn;

    @Schema(description = "캐피탈 활성화 여부")
    private YnType clanCapitalYn;

    @Schema(description = "병행클랜전 활성화 여부")
    private YnType clanWarParallelYn;

    @Schema(description = "대회 클랜 활성화 여부")
    private YnType clanCompetitionYn;
    
    public static ClanContent create(String tag) {
        return ClanContent.builder()
                          .tag(tag)
                          .clanWarYn(YnType.N)
                          .clanWarLeagueYn(YnType.N)
                          .clanCapitalYn(YnType.N)
                          .clanWarParallelYn(YnType.N)
                          .clanCompetitionYn(YnType.N)
                          .build();
    }

    public void update(ClanContentType type, YnType yn) {
        switch (type) {
            case CLAN_WAR -> this.changeClanWar(yn);
            case CLAN_WAR_LEAGUE -> this.changeClanWarLeague(yn);
            case CLAN_WAR_PARALLEL -> this.changeClanWarParallel(yn);
            case CLAN_CAPITAL -> this.changeClanCapital(yn);
        }
    }

    public void changeClanWar(YnType clanWarYn) {
        this.clanWarYn = clanWarYn;
    }

    public void changeClanWarLeague(YnType clanWarLeagueYn) {
        this.clanWarLeagueYn = clanWarLeagueYn;
    }

    public void changeClanWarParallel(YnType clanWarParallelYn) {
        this.clanWarParallelYn = clanWarParallelYn;
    }

    public void changeClanCapital(YnType clanCapitalYn) {
        this.clanCapitalYn = clanCapitalYn;
    }
}
