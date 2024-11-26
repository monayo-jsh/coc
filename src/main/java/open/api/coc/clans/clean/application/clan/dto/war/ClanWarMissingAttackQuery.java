package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMissingAttackSearchCriteria;
import open.api.coc.clans.common.exception.BadRequestException;
import open.api.coc.clans.domain.clans.converter.TimeUtils;

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

    public ClanWarMissingAttackSearchCriteria toSearchCriteria() {
        LocalDateTime from = TimeUtils.withMinTime(startDate);
        LocalDateTime to = TimeUtils.withMaxTime(endDate);

        return new ClanWarMissingAttackSearchCriteria(null, null, from ,to);
    }
}
