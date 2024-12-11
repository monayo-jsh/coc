package open.api.coc.clans.clean.application.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;

public record IconUrlCommand(

    @Schema(description = "아주 작은 아이콘 경로")
    String tiny,

    @Schema(description = "작은 아이콘 경로")
    String small,

    @Schema(description = "보통 아이콘 경로")
    String medium,

    @Schema(description = "큰 아이콘 경로")
    String large

) {

    public void validateAllFieldsEmpty() {
        if (StringUtils.hasText(tiny)) return;
        if (StringUtils.hasText(small)) return;
        if (StringUtils.hasText(medium)) return;
        if (StringUtils.hasText(large)) return;

        BadRequestException badRequestException = BadRequestException.create(ExceptionCode.INVALID_PARAMETER);
        badRequestException.addExtraMessage("아이콘 경로를 하나 이상 입력해주세요.");
        throw badRequestException;
    }

}
