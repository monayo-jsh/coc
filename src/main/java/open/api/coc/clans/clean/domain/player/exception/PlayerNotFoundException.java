package open.api.coc.clans.clean.domain.player.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class PlayerNotFoundException extends NotFoundException {

    public PlayerNotFoundException(String playerTag) {
        super("플레이어(%s) 정보 없음".formatted(playerTag));
    }
}
