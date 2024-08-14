package open.api.coc.external.coc.clan.domain.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class IconUrl {

    @Schema(description = "아주 작은 아이콘 경로")
    private String tiny;

    @Schema(description = "작은 아이콘 경로")
    private String small;

    @Schema(description = "보통 아이콘 경로")
    private String medium;

    @Schema(description = "큰 아이콘 경로")
    private String large;

    public void validateNotEmptyFields() {
        if (StringUtils.hasText(tiny)) return;
        if (StringUtils.hasText(small)) return;
        if (StringUtils.hasText(medium)) return;
        if (StringUtils.hasText(large)) return;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER);
    }
}
