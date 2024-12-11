package open.api.coc.clans.clean.domain.clan.repository;

import java.time.LocalDateTime;
import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantMissingAttackDTO;

public interface ClanWarMemberAttackRepository {

    List<ClanWarParticipantMissingAttackDTO> findAllByPeriod(LocalDateTime from, LocalDateTime to);
    List<ClanWarParticipantMissingAttackDTO> findAllByTag(String tag, LocalDateTime from, LocalDateTime to);
    List<ClanWarParticipantMissingAttackDTO> findAllByName(String name, LocalDateTime from, LocalDateTime to);

}
