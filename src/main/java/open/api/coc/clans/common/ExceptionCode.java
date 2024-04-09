package open.api.coc.clans.common;

import lombok.Getter;

@Getter
public enum ExceptionCode {

    EXTERNAL_ERROR("E000", "외부 연동 오류");

    private final String code;
    private final String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

}
