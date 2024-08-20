package open.api.coc.clans.domain.raid.query.strategy;

import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.database.repository.raid.RaiderQueryRepository;

public class RaiderScorePlayerTagQueryStrategy implements RaiderScoreQueryStrategy {

    @Override
    public List<RaiderEntity> execute(RaiderQueryRepository repository, String playerTag, Integer limit) {
        return repository.findByTag(playerTag, limit);
    }

}
