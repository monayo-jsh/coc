package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.external.coc.clan.domain.common.IconUrl;
import org.springframework.util.ObjectUtils;

@Getter
@AllArgsConstructor
public class ClanRequest {

    @JsonProperty("tag")
    private final String tag;

    @JsonProperty("name")
    private final String name;

    @JsonProperty("badge_url")
    private final IconUrl badgeUrl;

    public void validate() {
        if (isEmpty(tag)) {
            throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "클랜태그 미입력");
        }

        if (isEmpty(name)) {
            throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "클랜명 미입력");
        }
    }

    private boolean isEmpty(String value) {
        return ObjectUtils.isEmpty(value);
    }
}
