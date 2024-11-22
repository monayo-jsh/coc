package open.api.coc.clans.clean.domain.clan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.common.model.IconUrl;
import open.api.coc.clans.clean.domain.common.model.Label;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.database.entity.common.YnType;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Clan {

    @Schema(description = "클랜 태그")
    private String tag;

    @Schema(description = "클랜 이름")
    private String name;

    @Schema(description = "클랜 레벨")
    private Integer clanLevel;

    @Schema(description = "클랜 트로피 수")
    private Integer clanPoints;

    @Schema(description = "클랜 설명")
    private String description;

    @Schema(description = "클랜 가입유형")
    private String type;

    @Schema(description = "친선전유무")
    private Boolean isFamilyFriendly;

    @Schema(description = "전쟁기록 공개여부")
    private Boolean isWarLogPublic;

    @Schema(description = "전쟁빈도")
    private String warFrequency;

    @Schema(description = "가입요청 타운홀 레벨")
    private Integer requiredTownhallLevel;

    @Schema(description = "가입요청 트로피")
    private Integer requiredTrophies;

    @Schema(description = "전쟁 연속 승리수")
    private Integer warWinStreak;

    @Schema(description = "전쟁 승리수")
    private Integer warWins;

    @Schema(description = "전쟁 패배수")
    private Integer warLosses;

    @Schema(description = "전쟁 무승부수")
    private Integer warTies;

    @Schema(description = "클랜전 맥스쟁 여부")
    private boolean isMaxWar = false;

    @Schema(description = "클랜전 선점제 여부")
    private boolean isOccupy = false;

    @Schema(description = "리그 선점제 여부")
    private boolean isLeagueOccupy = false;

    @Schema(description = "전쟁 설명글")
    private String warDescription;

    @Schema(description = "클랜 멤버수")
    private Integer memberSize;
    
    @Schema(description = "클랜 정렬 순서")
    private Integer order;

    @Schema(description = "클랜 활성화여부")
    private YnType visibleYn;

    @Schema(description = "클랜 아이콘 객체")
    private IconUrl badgeUrl;

    @Schema(description = "클랜 리그 정보")
    private League warLeague;

    @Schema(description = "클랜 캐피탈 리그 정보")
    private League capitalLeague;

    @Schema(description = "클랜 캐피탈 트로피 수")
    private Integer clanCapitalPoints;

    @Schema(description = "클랜 캐피탈 정보")
    private ClanCapitalInfo clanCapital;

    @Schema(description = "클랜 컨텐츠 활성화 정보")
    private ClanContent clanContent;

    @Schema(description = "클랜 라벨 정보")
    private List<Label> labels;

    @Schema(description = "클랜 멤버 정보")
    private List<ClanMember> members;

    public void changeOrder(Integer order) {
        this.order = order;
    }

    public void createDefaultContent() {
        this.clanContent = ClanContent.create(this.tag);
    }

    public void changeWarLeague(League warLeague) {
        this.warLeague = warLeague;
    }

    public void changeClanCapital(ClanCapitalInfo clanCapitalInfo) {
        this.clanCapital = clanCapitalInfo;
    }

    public void changeCapitalPoints(Integer clanCapitalPoints) {
        this.clanCapitalPoints = clanCapitalPoints;
    }

    public void changeCapitalLeague(League capitalLeague) {
        this.capitalLeague = capitalLeague;
    }

    public void activate() {
        this.visibleYn = YnType.Y;
    }

    public void deactivate() {
        this.visibleYn = YnType.N;
    }

    public void changeLatestInfo(Clan latestClan) {
        this.changeWarLeague(latestClan.getWarLeague()); // 클랜 리그 정보 갱신
        this.changeClanCapital(latestClan.getClanCapital()); // 캐피탈 홀 레벨 갱신
        this.changeCapitalPoints(latestClan.getClanCapitalPoints()); // 캐피탈 트로피 점수 갱신
        this.changeCapitalLeague(latestClan.getCapitalLeague()); // 캐피탈 리그 갱신
    }

    public void changeContentActivation(YnType clanWarYn, YnType clanWarLeagueYn, YnType clanWarParallelYn, YnType clanCapitalYn) {
        Map<ClanContentType, YnType> updates = new HashMap<>(){
            {
                put(ClanContentType.CLAN_WAR, clanWarYn);
                put(ClanContentType.CLAN_WAR_LEAGUE, clanWarLeagueYn);
                put(ClanContentType.CLAN_WAR_PARALLEL, clanWarParallelYn);
                put(ClanContentType.CLAN_CAPITAL, clanCapitalYn);
            }
        };

        updates.forEach((type, yn) -> {
            if (yn != null) {
                this.clanContent.changeActivation(type, yn);
            }
        });

    }

    // 클랜 초기 상태 설정
    public void initDefault() {
        this.order = Integer.MAX_VALUE;
        this.createDefaultContent(); // 컨텐츠 활성화 기본값 설정
        this.activate(); // 활성화
    }
}
