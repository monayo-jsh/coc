package open.api.coc.clans.clean.presentation.clan.dto.war;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ClanWarMemberGetRequest(

    @NotBlank
    @Schema(description = "클랜 태그")
    String clanTag,

    @NotNull
    @Schema(description = "전쟁 시작일시")
    Long startTime,

    @Schema(description = "(Optional) 필수 참여 여부: YN")
    @Pattern(regexp = "[YN]")
    String necessaryAttackYn

) {

}
