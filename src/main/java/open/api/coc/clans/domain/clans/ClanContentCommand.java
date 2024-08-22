package open.api.coc.clans.domain.clans;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.common.ExceptionCode;
import open.api.coc.clans.common.exception.BadRequestException;
import org.springframework.util.ObjectUtils;

@Getter
@Builder
public class ClanContentCommand {

    private final String clanTag;
    private final String clanWarYn;
    private final String clanWarLeagueYn;
    private final String clanCapitalYn;
    private final String clanWarParallelYn;

    private ClanContentCommand(String clanTag, String clanWarYn, String clanWarLeagueYn, String clanCapitalYn,
                               String clanWarParallelYn) {
        this.clanTag = clanTag;
        this.clanWarYn = clanWarYn;
        this.clanWarLeagueYn = clanWarLeagueYn;
        this.clanCapitalYn = clanCapitalYn;
        this.clanWarParallelYn = clanWarParallelYn;
    }

    public static ClanContentCommand create(String clanTag, ClanContentRequest clanContentRequest) {
        ClanContentCommand clanContent = ClanContentCommand.builder()
                                                           .clanTag(clanTag)
                                                           .clanWarYn(clanContentRequest.getClanWarYn())
                                                           .clanWarLeagueYn(clanContentRequest.getClanWarLeagueYn())
                                                           .clanCapitalYn(clanContentRequest.getClanCapitalYn())
                                                           .clanWarParallelYn(clanContentRequest.getClanWarParallelYn())
                                                           .build();

        clanContent.validate();
        return clanContent;
    }

    private void validate() {
        if (validateYn(clanWarYn)) return;
        if (validateYn(clanWarLeagueYn)) return;
        if (validateYn(clanCapitalYn)) return;
        if (validateYn(clanWarParallelYn)) return;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "컨텐츠 항목 사용여부 미입력");
    }

    private boolean validateYn(String yn) {
        if (isEmpty(yn)) return false;

        if (Objects.equals("Y", yn)) return true;
        if (Objects.equals("N", yn)) return true;

        throw BadRequestException.create(ExceptionCode.INVALID_PARAMETER, "사용여부는 Y 또는 N 입력");
    }

    private boolean isEmpty(String value) {
        return ObjectUtils.isEmpty(value);
    }

}
