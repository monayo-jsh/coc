package open.api.coc.clans.clean.domain.capital.repository;

import java.time.LocalDate;
import java.util.List;
import open.api.coc.clans.clean.infrastructure.capital.persistence.dto.RaiderRankingDTO;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import org.springframework.data.domain.Pageable;

public interface ClanCapitalRaidMemberRepository {

    List<RaiderEntity> findAllByRaidId(Long raidId);

    List<RaiderEntity> findAllByPlayerTag(String playerTag, Integer limit);

    List<RaiderEntity> findAllByPlayerName(String playerName, Integer limit);

    List<RaiderRankingDTO> findAllResourceLootedRankingByStartDateAndPage(LocalDate startDate, Pageable pageable);

    List<RaiderRankingDTO> findAllResourceLootedAverageRankingByStartDateAndPage(List<LocalDate> startDates, Pageable pageable);

}
