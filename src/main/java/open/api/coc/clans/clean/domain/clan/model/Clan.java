package open.api.coc.clans.clean.domain.clan.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.domain.common.model.IconUrl;
import open.api.coc.clans.clean.domain.common.model.Label;
import open.api.coc.clans.clean.domain.league.model.League;

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

    @Schema(description = "클랜 아이콘 객체")
    private IconUrl badgeUrl;

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

    @Schema(description = "클랜 리그 정보")
    private League warLeague;

    @Schema(description = "클랜 라벨 정보")
    private List<Label> labels;

    @Schema(description = "클랜 캐피탈 리그 정보")
    private League capitalLeague;

    @Schema(description = "클랜 캐피탈 트로피 수")
    private Integer clanCapitalPoints;

    @Schema(description = "클랜 캐피탈 정보")
    private ClanCapitalInfo clanCapital;

    @Schema(description = "클랜 멤버수")
    private Integer memberSize;

    @Schema(description = "클랜 멤버 정보")
    private List<ClanMember> members;

    @Schema(description = "클랜 정렬 순서")
    private Integer order;

    @Schema(description = "클랜 컨텐츠 활성화 정보")
    private ClanContent clanContent;

    public void changeOrder(Integer order) {
        this.order = order;
    }

    public void createDefaultContent() {
        this.clanContent = ClanContent.create(this.tag);
    }
}
