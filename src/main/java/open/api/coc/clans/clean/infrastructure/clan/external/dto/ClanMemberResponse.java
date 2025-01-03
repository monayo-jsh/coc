package open.api.coc.clans.clean.infrastructure.clan.external.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.infrastructure.common.external.dto.LeagueResponse;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanMemberResponse {

    private String tag; // 태그
    private String name; // 이름
    private String role; // 직급

    private Integer townHallLevel; // 타운홀 레벨
    private Integer expLevel; // EXP 레벨

    private LeagueResponse league; // 리그 정보

    private Integer trophies; // 현재 트로피

    private Integer clanRank; // 클랜내 순위
    private Integer previousClanRank; // 클랜내 이전 순위

    private Integer donations; // 지원 수
    private Integer donationsReceived; // 지원 받은수

}
