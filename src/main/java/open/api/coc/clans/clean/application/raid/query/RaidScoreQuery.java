package open.api.coc.clans.clean.application.raid.query;


import open.api.coc.clans.clean.application.raid.query.strategy.RaiderScoreQueryStrategy;

public interface RaidScoreQuery {

    boolean hasCriteria();
    String getCriteria();
    RaiderScoreQueryStrategy getStrategy();

    default int getLimit() {
        return 4;
    }
}