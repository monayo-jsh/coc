package open.api.coc.clans.clean.application.clan.dto;

import lombok.AccessLevel;
import lombok.Builder;
import open.api.coc.clans.clean.presentation.clan.dto.ClanContentRequest;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.clans.database.entity.common.YnType;

@Builder(access = AccessLevel.PRIVATE)
public record ClanContentUpdateCommand(

    String clanTag,

    YnType clanWarYn,
    YnType clanWarLeagueYn,
    YnType clanWarParallelYn,
    YnType clanCapitalYn

) {

    public static ClanContentUpdateCommand create(String clanTag, ClanContentRequest clanContentRequest) {
        ClanContentUpdateCommand clanContent = ClanContentUpdateCommand.builder()
                                                                       .clanTag(clanTag)
                                                                       .clanWarYn(YnType.from(clanContentRequest.clanWarYn()))
                                                                       .clanWarLeagueYn(YnType.from(clanContentRequest.clanWarLeagueYn()))
                                                                       .clanWarParallelYn(YnType.from(clanContentRequest.clanWarParallelYn()))
                                                                       .clanCapitalYn(YnType.from(clanContentRequest.clanCapitalYn()))
                                                                       .build();

        clanContent.validate();
        return clanContent;
    }

    private void validate() {
        if (clanWarYn != null) return;
        if (clanWarLeagueYn != null) return;
        if (clanWarParallelYn != null) return;
        if (clanCapitalYn != null) return;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "컨텐츠 활성화 여부를 하나 이상 입력해주세요.");
    }

}
