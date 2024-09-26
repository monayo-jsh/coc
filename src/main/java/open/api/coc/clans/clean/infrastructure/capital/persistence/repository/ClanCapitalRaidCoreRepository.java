package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaidEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanCapitalRaidCoreRepository implements ClanCapitalRaidRepository {

    private final JpaRaidRepository jpaRaidRepository;
    private final JpaRaidCustomRepository jpaRaidCustomRepository;

    @Override
    public Optional<RaidEntity> findByClanTagAndStartDate(String clanTag, LocalDate startDate) {
        if (clanTag == null || clanTag.trim().isEmpty()) {
            throw new IllegalArgumentException("clanTag can not be null");
        }
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null");
        }

        return jpaRaidCustomRepository.findByClanTagAndStartDate(clanTag, startDate);
    }

    @Override
    public RaidEntity save(RaidEntity entity) {
        return jpaRaidRepository.save(entity);
    }

    @Override
    public void update(RaidEntity raidEntity) {
        jpaRaidCustomRepository.update(raidEntity);
    }

    @Override
    public LocalDate findLatestStartDate() {
        return jpaRaidCustomRepository.findLatestStartDate();
    }

    @Override
    public List<LocalDate> findLatestStartDates(Pageable pageable) {
        if (pageable == null) {
            throw new IllegalArgumentException("pageable can not be null");
        }

        return jpaRaidCustomRepository.findLatestStartDatesByPage(pageable);
    }

    @Override
    public List<RaidEntity> findAllWithRaiderByStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null");
        }

        return jpaRaidCustomRepository.findAllWithRaiderByStartDate(startDate);
    }

}
