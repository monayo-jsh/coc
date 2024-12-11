package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDate;
import open.api.coc.clans.common.exception.BadRequestException;

public record ClanWarQuery(

    LocalDate startDate,
    LocalDate endDate

) {

    public static ClanWarQuery create(LocalDate startDate, LocalDate endDate) {
        ClanWarQuery clanWarQuery = new ClanWarQuery(startDate, endDate);
        clanWarQuery.validate();
        return clanWarQuery;
    }

    private void validate() {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("요청 조회 기간을 확인해주세요");
        }
    }
}
