package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidMemberRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.dto.RaiderRankingDTO;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanCapitalRaidMemberCoreRepository implements ClanCapitalRaidMemberRepository {

    private final JpaRaiderRepository jpaRaiderRepository;
    private final JpaRaiderCustomRepository jpaRaiderCustomRepository;

    public List<RaiderEntity> findAllByRaidId(Long raidId) {
        if (raidId == null) {
            throw new IllegalArgumentException("raidId can not be null");
        }

        return jpaRaiderRepository.findAllByRaidId(raidId);
    }

    @Override
    public List<RaiderRankingDTO> findAllResourceLootedRankingByStartDateAndPage(LocalDate startDate, Pageable pageable) {
        if (startDate == null) {
            throw new IllegalArgumentException("startDate can not be null");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("pageable can not be null");
        }

        return jpaRaiderCustomRepository.findAllResourceLootedRankingByStartDateAndPage(startDate, pageable);
    }

    @Override
    public List<RaiderRankingDTO> findAllResourceLootedAverageRankingByStartDateAndPage(List<LocalDate> startDates, Pageable pageable) {
        if (startDates == null || startDates.isEmpty()) {
            throw new IllegalArgumentException("startDates can not be null");
        }
        if (pageable == null) {
            throw new IllegalArgumentException("pageable can not be null");
        }

        return jpaRaiderCustomRepository.findAllResourceLootedAverageRankingByStartDateAndPage(startDates, pageable);
    }

}
