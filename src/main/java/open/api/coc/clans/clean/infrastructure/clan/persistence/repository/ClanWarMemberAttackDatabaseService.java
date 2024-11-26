package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarParticipantMissingAttackDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarMemberAttackRepository;
import open.api.coc.clans.database.repository.clan.CLanWarMemberAttackQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarMemberAttackDatabaseService implements ClanWarMemberAttackRepository {

    private final CLanWarMemberAttackQueryRepository queryRepository;

    @Override
    public List<ClanWarParticipantMissingAttackDTO> findAllByPeriod(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttacksByStartTime(from, to);
    }

    @Override
    public List<ClanWarParticipantMissingAttackDTO> findAllByTag(String tag, LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttacksByTagAndStartTime(tag, from, to);
    }

    @Override
    public List<ClanWarParticipantMissingAttackDTO> findAllByName(String name, LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttacksByNameAndStartTime(name, from, to);
    }

}
