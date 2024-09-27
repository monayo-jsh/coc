package open.api.coc.clans.clean.domain.capital.service.impl;

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
import open.api.coc.clans.clean.domain.capital.service.ClanCapitalService;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanCapitalDBService implements ClanCapitalService {

    private final ClanCapitalRaidRepository clanCapitalRaidRepository;
    private final ClanCapitalRaidMapper clanCapitalRaidMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDate startDate) {
        Optional<RaidEntity> findRaidEntity = clanCapitalRaidRepository.findByClanTagAndStartDate(clanTag, startDate);

        if (findRaidEntity.isEmpty()) return Optional.empty();

        ClanCapitalRaid clanCapitalRaid = clanCapitalRaidMapper.toClanCapitalRaidWithMembers(findRaidEntity.get());
        return Optional.of(clanCapitalRaid);
    }

    @Override
    @Transactional
    public ClanCapitalRaid mergeRaidWithMember(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntityWithRaiderEntity(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toClanCapitalRaidWithMembers(saveRaidEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findAllByStartDate(LocalDate latestStartDate) {
        List<RaidEntity> raidEntities = clanCapitalRaidRepository.findAllWithRaiderByStartDate(latestStartDate);
        return raidEntities.stream()
                           .map(clanCapitalRaidMapper::toClanCapitalRaidWithMembers)
                           .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LocalDate findLatestStartDate() {
        LocalDate latestStartDate = clanCapitalRaidRepository.findLatestStartDate();
        if (Objects.isNull(latestStartDate)) {
            latestStartDate = LocalDate.now();
        }

        return latestStartDate;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocalDate> findAllStartDates(int countOfRecent) {
        Pageable pageable = Pageable.ofSize(countOfRecent);
        return clanCapitalRaidRepository.findLatestStartDates(pageable);
    }

    @Override
    @Transactional
    public ClanCapitalRaid createClanCapitalRaid(String clanTag, ClanCapitalRaidSeason currentSeason) {
        // 새로운 클랜 캐피탈 생성
        ClanCapitalRaid newClanCapitalRaid = ClanCapitalRaid.createNew(clanTag,
                                                                       currentSeason.getState(),
                                                                       currentSeason.getStartTime(),
                                                                       currentSeason.getEndTime());

        return create(newClanCapitalRaid);
    }

    private ClanCapitalRaid create(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntityOnly(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toClanCapitalRaid(saveRaidEntity);
    }

    @Override
    @Transactional
    public ClanCapitalRaid updateClanCapitalRaid(ClanCapitalRaid existingRaid, ClanCapitalRaidSeason currentSeason) {
        // 저장된 클랜 캐피탈 데이터 상태 비교 후 업데이트
        if (existingRaid.isDifferentState(currentSeason.getState())) {
            existingRaid.changeState(currentSeason.getState());
            updateOnlyRaid(existingRaid);
        }

        return existingRaid;
    }

    private void updateOnlyRaid(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toRaidEntityOnly(clanCapitalRaid);
        clanCapitalRaidRepository.update(raidEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, ClanCapitalRaid> findAllMapByIds(List<Long> raidIds) {
        List<RaidEntity> raidEntities = clanCapitalRaidRepository.findAllByIds(raidIds);
        return raidEntities.stream()
                           .map(clanCapitalRaidMapper::toClanCapitalRaid)
                           .collect(Collectors.toMap(ClanCapitalRaid::getId, raid -> raid));
    }

    @Override
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

    @Override
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

    @Override
    @Transactional
    public ClanCapitalRaid collectCurrentSeason(String clanTag, ClanCapitalRaidSeason currentSeason) {
        // 1. 클랜 캐피탈 조회 및 생성 또는 업데이트
        ClanCapitalRaid clanCapitalRaid = this.findByClanTagAndStartDate(clanTag, currentSeason.getStartTime().toLocalDate())
                                              .map(existingRaid -> updateClanCapitalRaid(existingRaid, currentSeason))
                                              .orElseGet(() -> createClanCapitalRaid(clanTag, currentSeason));

        // 클랜 캐피탈 참가자 정보 갱신
        clanCapitalRaid.updateParticipants(currentSeason.getMembers());

        // 클랜 캐피탈 참가자 데이터 업데이트
        ClanCapitalRaid updateClanCapitalRaid = mergeRaidWithMember(clanCapitalRaid);

        // 신규 참가자 아이디 매핑
        clanCapitalRaid.mappingParticipantIds(updateClanCapitalRaid.getMembers());

        // 반환
        return clanCapitalRaid;
    }
}
