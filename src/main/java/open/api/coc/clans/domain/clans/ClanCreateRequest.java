package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import open.api.coc.external.coc.clan.domain.common.IconUrl;

public record ClanCreateRequest(

    @Schema(description = "클랜 이름")
    @NotNull @NotEmpty
    String name,

    @Schema(description = "클랜 아이콘 정보 <br/>* 신규 등록 시 사용됨")
    @JsonProperty("badge_url")
    @NotNull
    IconUrl badgeUrl

) {
}
