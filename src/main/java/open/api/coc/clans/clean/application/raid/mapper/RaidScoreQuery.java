package open.api.coc.clans.clean.application.raid.mapper;


import open.api.coc.clans.clean.domain.capital.service.strategy.RaiderScoreQueryStrategy;

public interface RaidScoreQuery {

    boolean hasCriteria();
    String getCriteria();
    RaiderScoreQueryStrategy getStrategy();

    default int getLimit() {
        return 4;
    }
}