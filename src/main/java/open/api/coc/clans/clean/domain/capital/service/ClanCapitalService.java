package open.api.coc.clans.clean.domain.capital.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.model.ClanCapitalRaid;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import open.api.coc.clans.clean.infrastructure.capital.persistence.mapper.ClanCapitalRaidMapper;
import open.api.coc.clans.common.config.HallOfFameConfig;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClanCapitalService {

    private final HallOfFameConfig hallOfFameConfig;

    private final ClanCapitalRaidRepository clanCapitalRaidRepository;
    private final ClanCapitalRaidMapper clanCapitalRaidMapper;

    @Transactional(readOnly = true)
    public Optional<ClanCapitalRaid> findByClanTagAndStartDate(String clanTag, LocalDateTime startDateTime) {
        Optional<RaidEntity> findRaidEntity = clanCapitalRaidRepository.findByClanTagAndStartDate(clanTag, startDateTime.toLocalDate());

        if (findRaidEntity.isEmpty()) return Optional.empty();

        ClanCapitalRaid clanCapitalRaid = clanCapitalRaidMapper.toDomain(findRaidEntity.get());
        return Optional.of(clanCapitalRaid);
    }

    @Transactional
    public ClanCapitalRaid create(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toEntity(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);
        return clanCapitalRaidMapper.toDomain(saveRaidEntity);
    }

    @Transactional
    public ClanCapitalRaid update(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toEntity(clanCapitalRaid);
        RaidEntity saveRaidEntity = clanCapitalRaidRepository.save(raidEntity);

        return clanCapitalRaidMapper.toDomain(saveRaidEntity);
    }

    @Transactional
    public void updateRaid(ClanCapitalRaid clanCapitalRaid) {
        RaidEntity raidEntity = clanCapitalRaidMapper.toEntity(clanCapitalRaid);
        clanCapitalRaidRepository.update(raidEntity);
    }

    @Transactional(readOnly = true)
    public List<ClanCapitalRaid> findByStartDate(LocalDate latestStartDate) {
        List<RaidEntity> raidEntities = clanCapitalRaidRepository.findAllWithRaiderByStartDate(latestStartDate);
        return raidEntities.stream()
                           .map(clanCapitalRaidMapper::toDomain)
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
    public List<LocalDate> findAverageStartDates() {
        Pageable pageable = Pageable.ofSize(hallOfFameConfig.getAverage() + 1);
        List<LocalDate> startDates = clanCapitalRaidRepository.findLatestStartDates(pageable);

        // 수집된 캐피탈 시즌이 비어있거나 하나인경우 평균 랭킹 제공 불가
        if (startDates.isEmpty() || startDates.size() == 1) {
            return Collections.emptyList();
        }

        return startDates.subList(1, startDates.size());
    }
}
