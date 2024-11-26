package open.api.coc.clans.clean.domain.clan.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;

public class ClanAlreadyExistsException extends BadRequestException {

    public ClanAlreadyExistsException(String clanTag) {
        super(ExceptionCode.ALREADY_DATA.getCode(), "이미 등록된 클랜: %s".formatted(clanTag));
    }

}
