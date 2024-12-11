package open.api.coc.clans.clean.domain.clan.exception;

import open.api.coc.clans.common.exception.NotFoundException;

public class ClanNotExistsException extends NotFoundException {

    public ClanNotExistsException(String clanTag) {
        super("등록된 클랜(%s) 정보 없음".formatted(clanTag));
    }

}
