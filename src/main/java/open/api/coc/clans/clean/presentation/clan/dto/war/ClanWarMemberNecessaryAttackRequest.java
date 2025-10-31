package open.api.coc.clans.clean.presentation.clan.dto.war;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClanWarMemberNecessaryAttackRequest(

    @NotBlank(message = "클랜 태그를 입력해주세요.")
    String clanTag,

    @NotNull(message = "클랜 준비시작일을 입력해주세요.")
    Long preparationStartTime

) {
}
