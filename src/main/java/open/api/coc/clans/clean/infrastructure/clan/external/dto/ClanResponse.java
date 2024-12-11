package open.api.coc.clans.clean.infrastructure.clan.external.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.infrastructure.common.external.dto.IconUrlResponse;
import open.api.coc.clans.clean.infrastructure.common.external.dto.LabelResponse;
import open.api.coc.clans.clean.infrastructure.common.external.dto.LeagueResponse;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanResponse {

    private String tag; // 태그
    private String name; // 이름
    private String description; // 설명

    private Integer clanLevel; // 레벨
    private Integer clanPoints; // 총 트로피

    private IconUrlResponse badgeUrls; // 아이콘 정보

    private String type; // 가입 유형
    private Boolean isFamilyFriendly; // 친선전유무
    private Boolean isWarLogPublic; // 전쟁로그 공개여부
    private String warFrequency; // 전쟁빈도
    private Integer requiredTownhallLevel; // 가입 최소 타운홀 레벨
    private Integer requiredTrophies; // 가입 최소 트로피

    private Integer warWinStreak; // 클랜전 연속 승리수
    private Integer warWins; // 클랜전 승리수
    private Integer warLosses; // 클랜전 패배수
    private Integer warTies; // 클랜전 무승부수
    
    private LeagueResponse warLeague; // 클랜전 리그 정보
    private List<LabelResponse> labels; // 라벨 목록

    private LeagueResponse capitalLeague; // 캐피탈 리그 정보
    private Integer clanCapitalPoints; // 캐피탈 현재 트로피
    private ClanCapitalResponse clanCapital; // 클랜 캐피탈 정보

    private Integer members; // 클랜원 수
    private List<ClanMemberResponse> memberList; // 클랜원 목록

}
