package open.api.coc.clans.clean.infrastructure.capital.external.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanCapitalRaidSeasonResponse {

    private String state; // 캐피탈 상태
    private String startTime; // 캐피탈 시작일시
    private String endTime; // 캐피탈 종료일시
    private List<ClanCapitalRaidSeasonMemberResponse> members; // 캐피탈 참여자 목록

}
