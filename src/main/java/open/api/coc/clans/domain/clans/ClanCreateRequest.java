package open.api.coc.clans.domain.clans;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import open.api.coc.external.coc.clan.domain.common.IconUrl;

public record ClanCreateRequest(

    @JsonProperty("name") @NotNull @NotEmpty String name,
    @JsonProperty("badge_url") @NotNull IconUrl badgeUrl

) {
}
