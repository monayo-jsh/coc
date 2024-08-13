package open.api.coc.external.coc.clan.domain.common;

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

    private String tiny;
    private String small;
    private String medium;
    private String large;

    public void validateNotEmptyFields() {
        if (StringUtils.hasText(tiny)) return;
        if (StringUtils.hasText(small)) return;
        if (StringUtils.hasText(medium)) return;
        if (StringUtils.hasText(large)) return;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER);
    }
}
