package open.api.coc.clans.clean.application.raid.dto;

import open.api.coc.clans.clean.application.raid.mapper.RaidScoreQuery;
import open.api.coc.clans.clean.domain.capital.service.strategy.RaiderScorePlayerTagQueryStrategy;
import open.api.coc.clans.clean.domain.capital.service.strategy.RaiderScoreQueryStrategy;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;

public class RaidScorePlayerTagQuery implements RaidScoreQuery {

    private final RaiderScoreQueryStrategy strategy;
    private final String playerTag;

    private RaidScorePlayerTagQuery(String playerTag) {
        this.strategy = new RaiderScorePlayerTagQueryStrategy();
        this.playerTag = playerTag;
    }

    public static RaidScoreQuery create(String playerTag) {
        RaidScorePlayerTagQuery query = new RaidScorePlayerTagQuery(playerTag);
        query.validate();
        return query;
    }

    private void validate() {
        if (hasCriteria()) return;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "playerTag를 입력해주세요.");
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