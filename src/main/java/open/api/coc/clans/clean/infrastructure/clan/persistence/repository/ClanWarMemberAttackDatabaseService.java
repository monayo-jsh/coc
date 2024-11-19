package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.model.ClanWarMemberMissingAttackDTO;
import open.api.coc.clans.clean.domain.clan.repository.ClanWarMemberAttackRepository;
import open.api.coc.clans.database.repository.clan.CLanWarMemberAttackQueryRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanWarMemberAttackDatabaseService implements ClanWarMemberAttackRepository {

    private final CLanWarMemberAttackQueryRepository queryRepository;

    @Override
    public List<ClanWarMemberMissingAttackDTO> findAllByPeriod(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttacksByStartTime(from, to);
    }

    @Override
    public List<ClanWarMemberMissingAttackDTO> findAllByTag(String tag, LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttacksByTagAndStartTime(tag, from, to);
    }

    @Override
    public List<ClanWarMemberMissingAttackDTO> findAllByName(String name, LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttacksByNameAndStartTime(name, from, to);
    }

}
