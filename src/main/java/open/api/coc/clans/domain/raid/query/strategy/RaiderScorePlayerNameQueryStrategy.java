package open.api.coc.clans.domain.raid.query.strategy;

import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.database.repository.raid.RaiderQueryRepository;

public class RaiderScorePlayerNameQueryStrategy implements RaiderScoreQueryStrategy {

    @Override
    public List<RaiderEntity> execute(RaiderQueryRepository repository, String playerName, Integer limit) {
        return repository.findByName(playerName, limit);
    }

}
