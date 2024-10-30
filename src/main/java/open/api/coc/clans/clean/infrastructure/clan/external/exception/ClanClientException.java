package open.api.coc.clans.clean.infrastructure.clan.external.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;

public class ClanClientException extends CustomRuntimeException {

    public ClanClientException(String clanTag) {
        super(ExceptionCode.EXTERNAL_ERROR.getCode(), "클랜 외부 연동 실패: %s".formatted(clanTag));
    }

}
