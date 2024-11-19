package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;

public interface ClanWarRepository {

    Optional<ClanWarEntity> findById(Long warId);
    List<ClanWarDTO> findAllDTOByStartTime(LocalDateTime from, LocalDateTime to);

    Optional<ClanWarDTO> findDTOWithAllById(Long warId);
    Optional<ClanWarDTO> findDTOWithAllByClanTagAndStartTime(String clanTag, LocalDateTime startTime);

    ClanWarEntity save(ClanWarEntity clanWar);

    Map<String, Integer> findLeagueWarRoundCountMap(LocalDateTime from, LocalDateTime to);

}
