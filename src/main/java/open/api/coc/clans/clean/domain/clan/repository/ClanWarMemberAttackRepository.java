package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;

public interface ClanWarMemberAttackRepository {

    List<ClanWarMemberMissingAttackDTO> findAllByPeriod(LocalDateTime from, LocalDateTime to);
    List<ClanWarMemberMissingAttackDTO> findAllByTag(String tag, LocalDateTime from, LocalDateTime to);
    List<ClanWarMemberMissingAttackDTO> findAllByName(String name, LocalDateTime from, LocalDateTime to);

}
