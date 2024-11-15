package open.api.coc.clans.clean.domain.clan.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class ClanWarNotExistsException extends NotFoundException {

    public ClanWarNotExistsException(Long warId) {
        super("클랜전(%s) 정보 없음".formatted(warId));
    }

}
