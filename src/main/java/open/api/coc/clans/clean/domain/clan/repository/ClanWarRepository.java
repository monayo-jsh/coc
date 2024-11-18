package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;

public interface ClanWarRepository {

    List<ClanWarDTO> findAllDTOByStartTime(LocalDateTime from, LocalDateTime to);

    Optional<ClanWarDTO> findDTOWithAllById(Long warId);
    Optional<ClanWarDTO> findDTOWithAllByClanTagAndStartTime(String clanTag, LocalDateTime startTime);

}
