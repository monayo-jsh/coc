package open.api.coc.clans.clean.infrastructure.clan.external.exception;

import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.CustomRuntimeException;

public class ClanClientException extends CustomRuntimeException {

    private ClanClientException(String message) {
        super(ExceptionCode.EXTERNAL_ERROR.getCode(), message);
    }

    public static ClanClientException ofClan(String clanTag) {
        String errorMessage = "클랜 요청 실패: %s".formatted(clanTag);
        return new ClanClientException(errorMessage);
    }

    public static ClanClientException ofClanMember(String clanTag) {
        String errorMessage = "클랜 멤버 요청 실패: %s".formatted(clanTag);
        return new ClanClientException(errorMessage);
    }
}
