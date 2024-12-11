package open.api.coc.clans.clean.domain.player.exception;

import open.api.coc.clans.common.exception.BadRequestException;

public class PlayerNotLeagueException extends BadRequestException {

    public PlayerNotLeagueException(String playerTag) {
        super("현재 리그 배정되지 않은 계정: %s".formatted(playerTag));
    }
}
