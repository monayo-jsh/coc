package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanLeagueAssignRepository;
import open.api.coc.clans.database.entity.clan.ClanAssignedPlayerPK;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanLeagueAssignDatabaseService implements ClanLeagueAssignRepository {

    private final JpaClanLeagueAssignedPlayerRepository jpaClanLeagueAssignedPlayerRepository;
    private final JpaClanLeagueAssignedPlayerQueryRepository jpaClanLeagueAssignedPlayerQueryRepository;

    @Override
    public String findLatestSeasonDate() {
        return jpaClanLeagueAssignedPlayerQueryRepository.findLatestSeasonDate();
    }

    @Override
    public void cancel(String seasonDate, String playerTag) {
        jpaClanLeagueAssignedPlayerRepository.deleteById(ClanAssignedPlayerPK.of(seasonDate, playerTag));
    }
}
