package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;

public interface ClanCapitalService {

    // 클랜 캐피탈 목록을 키 & 맵 형태로 조회한다.
    Map<Long, ClanCapitalRaid> findAllMapByIds(List<Long> raidIds);

    // 클랜 캐피탈 목록을 클랜 태그와 캐피탈 시작일자로 조회한다.
    Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDate startDate);

    // 클랜 캐피탈 목록을 캐피탈 시작일자로 조회한다.
    List<ClanCapitalRaid> findAllByStartDate(LocalDate startDate);

    // 지난주 클랜 캐피탈 목록(참여자 포함)을 조회한다.
    List<ClanCapitalRaid> findAllWithMembersFromLastWeek();

    // 현재 시즌의 캐피탈 중 동기화가 필요한 목록을 조회한다.
    List<ClanCapitalRaid> findAllThatNeedSync();

    // 수집된 최신 캐피탈 시작일자를 조회한다.
    LocalDate findLatestStartDate();

    // 수집된 최신 캐피탈 시작일자 목록을 조회한다.
    List<LocalDate> findAllStartDates(int countOfRecent);

    // 클랜 캐피탈을 서버에 수집한다.
    ClanCapitalRaid collectCurrentSeason(String clanTag, ClanCapitalRaidSeason currentSeason);

    // 클랜 캐피탈을 서버에 생성한다.
    ClanCapitalRaid createClanCapitalRaid(String clanTag, ClanCapitalRaidSeason currentSeason);

    // 클랜 캐피탈을 서버에 수정한다.
    ClanCapitalRaid updateClanCapitalRaid(ClanCapitalRaid existingRaid, ClanCapitalRaidSeason currentSeason);

    // 클랜 캐피탈 (참여자) 정보를 수정한다.
    ClanCapitalRaid mergeRaidWithMember(ClanCapitalRaid clanCapitalRaid);

}
