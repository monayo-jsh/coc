package open.api.coc.clans.domain.raid.query;

import open.api.coc.clans.domain.raid.query.strategy.RaiderScoreQueryStrategy;

public interface RaiderScoreQuery {

    boolean hasCriteria();
    String getCriteria();
    RaiderScoreQueryStrategy getStrategy();

}