package open.api.coc.clans.clean.infrastructure.capital.persistence.repository;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.capital.repository.ClanCapitalRaidMemberRepository;
import open.api.coc.clans.clean.infrastructure.capital.persistence.dto.RaiderRankingDTO;
import open.api.coc.clans.clean.infrastructure.capital.persistence.entity.RaiderEntity;
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
    public List<RaiderEntity> findAllByPlayerTag(String playerTag, Integer limit) {
        if (playerTag == null) {
            throw new IllegalArgumentException("playerTag can not be null");
        }

        return jpaRaiderRepository.findAllByPlayerTag(playerTag, limit);
    }

    @Override
    public List<RaiderEntity> findAllByPlayerName(String playerName, Integer limit) {
        if (playerName == null) {
            throw new IllegalArgumentException("playerName can not be null");
        }

        return jpaRaiderRepository.findAllByPlayerName(playerName, limit);
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
