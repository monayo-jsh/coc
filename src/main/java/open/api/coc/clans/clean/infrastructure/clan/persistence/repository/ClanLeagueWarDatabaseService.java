package open.api.coc.clans.clean.infrastructure.clan.persistence.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.clan.repository.ClanLeagueWarRepository;
import open.api.coc.clans.clean.infrastructure.clan.persistence.entity.ClanLeagueWarEntity;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClanLeagueWarDatabaseService implements ClanLeagueWarRepository {

    private final JpaClanLeagueWarQueryRepository queryRepository;

    @Override
    public List<ClanLeagueWarEntity> findAllBySeason(String season) {
        return queryRepository.findAllBySeason(season);
    }
}
