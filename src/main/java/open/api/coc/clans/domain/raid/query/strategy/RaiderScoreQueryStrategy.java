package open.api.coc.clans.domain.raid.query.strategy;

import java.util.List;
import open.api.coc.clans.database.entity.raid.RaiderEntity;
import open.api.coc.clans.database.repository.raid.RaiderQueryRepository;

public interface RaiderScoreQueryStrategy {

    List<RaiderEntity> execute(RaiderQueryRepository repository, String criteria, Integer limit);

}