package open.api.coc.clans.common;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    ERROR_NOT_FOUND("I4040", "정보 없음"),

    EXTERNAL_ERROR("E5000", "외부 연동 오류");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
