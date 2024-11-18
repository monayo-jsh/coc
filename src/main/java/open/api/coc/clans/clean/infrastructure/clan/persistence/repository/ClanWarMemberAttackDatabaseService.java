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
    public List<ClanWarMemberMissingAttackDTO> findMissingAttackPlayers(LocalDateTime from, LocalDateTime to) {
        return queryRepository.findMissingAttackPlayers(from, to);
    }

}
