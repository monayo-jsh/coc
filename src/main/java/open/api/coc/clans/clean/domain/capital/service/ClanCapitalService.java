package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanCapitalService {

    private final ClanCapitalRaidRepository clanCapitalRaidRepository;
    private final ClanCapitalRaidMapper clanCapitalRaidMapper;

    @Transactional(readOnly = true)
    public Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDateTime startDateTime) {
        Optional<RaidEntity> findRaidEntity = clanCapitalRaidRepository.findByClanTagAndStartDate(clanTag, startDateTime.toLocalDate());

        if (findRaidEntity.isEmpty()) return Optional.empty();

        ClanCapitalRaid clanCapitalRaid = clanCapitalRaidMapper.toClanCapitalRaidWithMembers(findRaidEntity.get());
        return Optional.of(clanCapitalRaid);
    }

    @Transactional
    public ClanCapitalRaid create(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntityOnly(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toClanCapitalRaid(saveRaidEntity);
    }

    @Transactional
    public ClanCapitalRaid updateWithMember(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntityWithRaiderEntity(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toClanCapitalRaidWithMembers(saveRaidEntity);
    }

    @Transactional
    public void updateOnlyRaid(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntityOnly(clanCapitalRaid);
        clanCapitalRaidRepository.update(raidEntity);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findByStartDate(LocalDate latestStartDate) {
        List<RaidEntity> raidEntities = clanCapitalRaidRepository.findAllWithRaiderByStartDate(latestStartDate);
        return raidEntities.stream()
                           .map(clanCapitalRaidMapper::toClanCapitalRaidWithMembers)
                           .toList();
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
    public List<LocalDate> findStartDates(int countOfRecent) {
        Pageable pageable = Pageable.ofSize(countOfRecent);
        return clanCapitalRaidRepository.findLatestStartDates(pageable);
    }

    @Transactional
    public ClanCapitalRaid createClanCapitalRaid(String clanTag, ClanCapitalRaidSeason currentSeason) {

        // 새로운 클랜 캐피탈 생성
        ClanCapitalRaid newClanCapitalRaid = ClanCapitalRaid.createNew(clanTag,
                                                                       currentSeason.getState(),
                                                                       currentSeason.getStartTime(),
                                                                       currentSeason.getEndTime());

        return create(newClanCapitalRaid);
    }

    @Transactional
    public ClanCapitalRaid updateClanCapitalRaid(ClanCapitalRaid existingRaid, ClanCapitalRaidSeason currentSeason) {
        // 저장된 클랜 캐피탈 데이터 상태 비교 후 업데이트
        if (existingRaid.isDifferentState(currentSeason.getState())) {
            existingRaid.changeState(currentSeason.getState());
            updateOnlyRaid(existingRaid);
        }

        return existingRaid;
    }

    @Transactional(readOnly = true)
    public Map<Long, ClanCapitalRaid> findAllMapByIds(List<Long> raidIds) {
        List<RaidEntity> raidEntities = clanCapitalRaidRepository.findAllByIds(raidIds);
        return raidEntities.stream()
                           .map(clanCapitalRaidMapper::toClanCapitalRaid)
                           .collect(Collectors.toMap(ClanCapitalRaid::getId, raid -> raid));
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findAllWithMembersFromLastWeek() {
        // 현재 서버에 수집된 최근 시작 날짜 2주치를 조회한다.
        int searchCountOfRecent = 2;
        Pageable pageable = Pageable.ofSize(searchCountOfRecent);
        List<LocalDate> latestStartDates = clanCapitalRaidRepository.findLatestStartDates(pageable);

        if (latestStartDates.isEmpty() || latestStartDates.size() < 2) {
            // 최근 날짜가 비어있거나 하나인 경우 지난주 위반 항목 제공 불가
            return Collections.emptyList();
        }

        // 수집 시작일을 기준으로 지난주 시작일자 획득
        LocalDate lastWeekStartDate = latestStartDates.get(1);

        // 지난주 진행된 캐피탈 목록 조회
        List<RaidEntity> raidEntities = clanCapitalRaidRepository.findAllWithRaiderByStartDate(lastWeekStartDate);

        // 결과 반환
        return raidEntities.stream()
                           .map(clanCapitalRaidMapper::toClanCapitalRaidWithMembers)
                           .toList();
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findAllThatNeedSync() {
        // 수집된 캐피탈 최근 시작일자를 조회한다.
        LocalDate latestStartDate = findLatestStartDate();

        // 캐피탈 목록을 조회한다.
        List<ClanCapitalRaid> raids = findByStartDate(latestStartDate);

        // 종료되지 않은 상태의 캐피탈 목록을 반환한다.
        return raids.stream()
                    .filter(ClanCapitalRaid::isNotEnded)
                    .toList();
    }

}
