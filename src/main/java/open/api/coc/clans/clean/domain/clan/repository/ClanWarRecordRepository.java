package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberRecordDTO;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import org.springframework.data.domain.Pageable;

public interface ClanWarRecordRepository {

    List<ClanWarMemberRecordDTO> findAll(ClanWarType type, LocalDateTime from, LocalDateTime to, Pageable pageable);
    List<ClanWarMemberRecordDTO> findAllByClanTag(String clanTag, ClanWarType type, LocalDateTime from, LocalDateTime to, Pageable pageable);

}
