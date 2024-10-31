package open.api.coc.clans.clean.domain.player.exception;

import open.api.coc.clans.common.exception.BadRequestException;

public class PlayerNotLegendLeagueException extends BadRequestException {

    public PlayerNotLegendLeagueException() {
        super("전설 리그에 배정된 계정만 등록 가능합니다.");
    }
}
