package open.api.coc.clans.clean.infrastructure.capital.external.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;

public class ClanCapitalRaidSeasonException extends CustomRuntimeException {

    public ClanCapitalRaidSeasonException(String clanTag) {
        super(ExceptionCode.EXTERNAL_ERROR.getCode(), "클랜 캐피탈 조회 실패: %s".formatted(clanTag));
    }

}
