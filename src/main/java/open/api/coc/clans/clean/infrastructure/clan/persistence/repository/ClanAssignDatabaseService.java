package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanAssignRepository;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPK;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanAssignDatabaseService implements ClanAssignRepository {

    private final JpaClanAssignedPlayerRepository jpaClanAssignedPlayerRepository;
    private final JpaClanAssignedPlayerQueryRepository jpaClanAssignedPlayerQueryRepository;

    @Override
    public String findLatestSeasonDate() {
        return jpaClanAssignedPlayerQueryRepository.findLatestSeasonDate();
    }

    @Override
    public void cancel(String seasonDate, String playerTag) {
        jpaClanAssignedPlayerRepository.deleteById(ClanAssignedPlayerPK.of(seasonDate, playerTag));
    }
}
