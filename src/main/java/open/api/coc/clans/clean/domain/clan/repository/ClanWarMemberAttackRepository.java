package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;

public interface ClanWarMemberAttackRepository {

    List<ClanWarMemberMissingAttackDTO> findMissingAttacksByPeriod(LocalDateTime from, LocalDateTime to);
    List<ClanWarMemberMissingAttackDTO> findMissingAttacksByTag(String tag, LocalDateTime from, LocalDateTime to);
    List<ClanWarMemberMissingAttackDTO> findMissingAttacksByName(String name, LocalDateTime from, LocalDateTime to);
}
