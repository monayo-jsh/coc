package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;

public interface ClanWarMemberAttackRepository {

    List<ClanWarMemberMissingAttackDTO> findMissingAttackPlayers(LocalDateTime from, LocalDateTime to);

}
