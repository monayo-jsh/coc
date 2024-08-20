package open.api.coc.clans.domain.raid.query;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.domain.raid.query.strategy.RaiderScorePlayerNameQueryStrategy;
import open.api.coc.clans.domain.raid.query.strategy.RaiderScoreQueryStrategy;
import org.springframework.util.StringUtils;

public class RaiderScorePlayerNameQuery implements RaiderScoreQuery {

    private final RaiderScoreQueryStrategy strategy;
    private final String playerName;

    private RaiderScorePlayerNameQuery(String playerName) {
        this.strategy = new RaiderScorePlayerNameQueryStrategy();
        this.playerName = playerName;
    }

    public static RaiderScoreQuery create(String playerName) {
        RaiderScorePlayerNameQuery query = new RaiderScorePlayerNameQuery(playerName);
        query.validate();
        return query;
    }

    private void validate() {
        if (hasCriteria()) return;

        throw CustomRuntimeException.create(ExceptionCode.INVALID_PARAMETER, "playerName를 입력해주세요.");
    }

    @Override
    public boolean hasCriteria() {
        return StringUtils.hasText(this.playerName);
    }

    @Override
    public String getCriteria() {
        return this.playerName;
    }

    @Override
    public RaiderScoreQueryStrategy getStrategy() {
        return strategy;
    }

}