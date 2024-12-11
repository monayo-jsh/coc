package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.external.model.ClanCapitalRaidSeason;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanCapitalService {

    private final ClanCapitalRaidRepository clanCapitalRaidRepository;
    
    @Transactional(readOnly = true)
    public Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDate startDate) {
        if (clanTag == null || clanTag.trim().isEmpty()) {
            throw new IllegalArgumentException("clanTag can not be null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null");
        }

        return clanCapitalRaidRepository.findByClanTagAndStartDate(clanTag, startDate);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findAllByStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null");
        }

        return clanCapitalRaidRepository.findAllWithRaiderByStartDate(startDate);
    }

    
    @Transactional(readOnly = true)
    public LocalDate findLatestStartDate() {
        LocalDate latestStartDate = clanCapitalRaidRepository.findLatestStartDate();
        if (Objects.isNull(latestStartDate)) {
            latestStartDate = LocalDate.now();
        }

        return latestStartDate;
    }

    
    @Transactional(readOnly = true)
    public List<LocalDate> findAllLatestStartDateByCount(int countOfRecent) {
        Pageable pageable = Pageable.ofSize(countOfRecent);
        return clanCapitalRaidRepository.findAllLatestStartDate(pageable);
    }

    
    @Transactional
    public ClanCapitalRaid createClanCapitalRaid(String clanTag, ClanCapitalRaidSeason currentSeason) {
        if (clanTag == null || clanTag.trim().isEmpty()) {
            throw new IllegalArgumentException("clanTag can not be null");
        }

        // 새로운 클랜 캐피탈 생성
        ClanCapitalRaid clanCapitalRaid = ClanCapitalRaid.createNew(clanTag,
                                                                    currentSeason.getState(),
                                                                    currentSeason.getStartTime().toLocalDate(),
                                                                    currentSeason.getEndTime().toLocalDate(),
                                                                    currentSeason.getMembers());

        // 저장
        ClanCapitalRaid newClanCapitalRaid = clanCapitalRaidRepository.save(clanCapitalRaid);

        // 신규 캐피탈 고유키 매핑
        clanCapitalRaid.assignIdIfAbsent(newClanCapitalRaid.getId());

        // 신규 참가자 아이디 매핑
        clanCapitalRaid.mappingParticipantIds(newClanCapitalRaid.getMembers());

        return clanCapitalRaid;
    }

    @Transactional
    public ClanCapitalRaid updateClanCapitalRaid(ClanCapitalRaid existingRaid, ClanCapitalRaidSeason currentSeason) {
        // 클랜 캐피탈 정보 갱신
        existingRaid.changeRaidInfo(currentSeason);

        // 클랜 캐피탈 참가자 데이터 업데이트
        ClanCapitalRaid updateRaid = clanCapitalRaidRepository.save(existingRaid);

        // 신규 참가자 아이디 매핑
        existingRaid.mappingParticipantIds(updateRaid.getMembers());

        return existingRaid;
    }

    
    @Transactional(readOnly = true)
    public Map<Long, ClanCapitalRaid> findAllMapByIds(List<Long> raidIds) {
        if (raidIds == null || raidIds.isEmpty()) {
            throw new IllegalArgumentException("raidIds can not be null or empty");
        }

        List<ClanCapitalRaid> clanCapitalRaids = clanCapitalRaidRepository.findAllByIds(raidIds);
        return clanCapitalRaids.stream()
                               .collect(Collectors.toMap(ClanCapitalRaid::getId, raid -> raid));
    }

    
    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findAllWithMembersFromLastWeek() {
        // 현재 서버에 수집된 최근 시작 날짜 2주치를 조회한다.
        int searchCountOfRecent = 2;
        Pageable pageable = Pageable.ofSize(searchCountOfRecent);
        List<LocalDate> latestStartDates = clanCapitalRaidRepository.findAllLatestStartDate(pageable);

        if (latestStartDates.isEmpty() || latestStartDates.size() < 2) {
            // 최근 날짜가 비어있거나 하나인 경우 지난주 위반 항목 제공 불가
            return Collections.emptyList();
        }

        // 수집 시작일을 기준으로 지난주 시작일자 획득
        LocalDate lastWeekStartDate = latestStartDates.get(1);

        // 지난주 진행된 캐피탈 목록 조회
        return clanCapitalRaidRepository.findAllWithRaiderByStartDate(lastWeekStartDate);
    }

    
    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findAllThatNeedSync() {
        // 수집된 캐피탈 최근 시작일자를 조회한다.
        LocalDate latestStartDate = findLatestStartDate();

        // 캐피탈 목록을 조회한다.
        List<ClanCapitalRaid> raids = findAllByStartDate(latestStartDate);

        // 종료되지 않은 상태의 캐피탈 목록을 반환한다.
        return raids.stream()
                    .filter(ClanCapitalRaid::isNotEnded)
                    .toList();
    }

    
    @Transactional
    public ClanCapitalRaid collectCurrentSeason(String clanTag, ClanCapitalRaidSeason currentSeason) {
        // 클랜 캐피탈 조회 결과에 따라서 생성 또는 업데이트
        return this.findByClanTagAndStartDate(clanTag, currentSeason.getStartTime().toLocalDate())
                   .map(existingRaid -> updateClanCapitalRaid(existingRaid, currentSeason))
                   .orElseGet(() -> createClanCapitalRaid(clanTag, currentSeason));
    }
}
