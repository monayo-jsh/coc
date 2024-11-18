package open.api.coc.clans.clean.application.clan.dto;

import java.time.LocalDate;
import open.api.coc.clans.common.exception.BadRequestException;

public record ClanWarMissingAttackQuery(

    LocalDate startDate,
    LocalDate endDate

) {

    public static ClanWarMissingAttackQuery create(LocalDate startDate, LocalDate endDate) {
        ClanWarMissingAttackQuery query = new ClanWarMissingAttackQuery(startDate, endDate);
        query.validate();
        return query;
    }

    private void validate() {
        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("요청 조회 기간을 확인해주세요");
        }
    }
}
