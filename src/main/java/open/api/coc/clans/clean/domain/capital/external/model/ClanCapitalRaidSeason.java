package open.api.coc.clans.clean.domain.capital.external.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ClanCapitalRaidSeason {

    private String state; // 캐피탈 상태
    private LocalDateTime startTime; // 캐피탈 시작일시
    private LocalDateTime endTime; // 캐피탈 종료일시
    private List<ClanCapitalRaidSeasonMember> members; // 캐피탈 참여자 목록

}
