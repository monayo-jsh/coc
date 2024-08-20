package open.api.coc.clans.domain.raid.query;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;
import open.api.coc.clans.domain.raid.query.strategy.RaiderScorePlayerTagQueryStrategy;
import open.api.coc.clans.domain.raid.query.strategy.RaiderScoreQueryStrategy;
import org.springframework.util.StringUtils;

public class RaiderScorePlayerTagQuery implements RaiderScoreQuery {

    private final RaiderScoreQueryStrategy strategy;
    private final String playerTag;

    private RaiderScorePlayerTagQuery(String playerTag) {
        this.strategy = new RaiderScorePlayerTagQueryStrategy();
        this.playerTag = playerTag;
    }

    public static RaiderScoreQuery create(String playerTag) {
        RaiderScorePlayerTagQuery query = new RaiderScorePlayerTagQuery(playerTag);
        query.validate();
        return query;
    }

    private void validate() {
        if (hasCriteria()) return;

        throw CustomRuntimeException.create(ExceptionCode.INVALID_PARAMETER, "playerTag를 입력해주세요.");
    }

    @Override
    public boolean hasCriteria() {
        return StringUtils.hasText(this.playerTag);
    }

    @Override
    public String getCriteria() {
        return this.playerTag;
    }

    @Override
    public RaiderScoreQueryStrategy getStrategy() {
        return strategy;
    }

}