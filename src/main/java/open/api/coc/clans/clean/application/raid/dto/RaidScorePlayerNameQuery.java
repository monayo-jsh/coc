package open.api.coc.clans.clean.application.raid.dto;

import open.api.coc.clans.clean.application.raid.mapper.RaidScoreQuery;
import open.api.coc.clans.clean.domain.capital.service.strategy.RaiderScorePlayerNameQueryStrategy;
import open.api.coc.clans.clean.domain.capital.service.strategy.RaiderScoreQueryStrategy;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;

public class RaidScorePlayerNameQuery implements RaidScoreQuery {

    private final RaiderScoreQueryStrategy strategy;
    private final String playerName;

    private RaidScorePlayerNameQuery(String playerName) {
        this.strategy = new RaiderScorePlayerNameQueryStrategy();
        this.playerName = playerName;
    }

    public static RaidScoreQuery create(String playerName) {
        RaidScorePlayerNameQuery query = new RaidScorePlayerNameQuery(playerName);
        query.validate();
        return query;
    }

    private void validate() {
        if (hasCriteria()) return;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "playerName를 입력해주세요.");
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