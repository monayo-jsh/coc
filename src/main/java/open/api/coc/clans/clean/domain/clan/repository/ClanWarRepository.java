package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import open.api.coc.clans.clean.domain.clan.model.ClanWarDTO;
import open.api.coc.clans.database.entity.clan.ClanWarEntity;

public interface ClanWarRepository {

    List<ClanWarEntity> findAllByStartTime(LocalDateTime from, LocalDateTime to);

    Optional<ClanWarDTO> findDTOWithAllById(Long warId);
}
