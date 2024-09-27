package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.util.List;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMember;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaidMemberRankingDTO;

public interface ClanCapitalMemberService {

    // 캐피탈 참여자 목록을 캐피탈 고유키로 조회한다.
    List<ClanCapitalRaidMember> findByRaidId(Long raidId);

    // 캐피탈 참여자 정보를 사용자 태그로 한다.
    List<ClanCapitalRaidMember> findByTag(String playerTag, Integer limit);

    // 캐피탈 참여자 정보를 이름으로 조회한다.
    List<ClanCapitalRaidMember> findByName(String playerName, Integer limit);

    // 캐피탈 획득 점수 랭킹을 시작일자로 조회한다.
    List<ClanCapitalRaidMemberRankingDTO> rankingResourceLootedByStartDate(LocalDate startDate);

    // 캐피탈 획득 점수 평균 랭킹을 시작일자 목록으로 조회한다.
    List<ClanCapitalRaidMemberRankingDTO> rankingResourceLootedAverageByStartDates(List<LocalDate> startDates);

}
