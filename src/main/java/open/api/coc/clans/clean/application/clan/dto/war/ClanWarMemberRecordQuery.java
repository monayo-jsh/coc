package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMemberRecordSearchCriteria;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.domain.clans.converter.TimeUtils;

public record ClanWarMemberRecordQuery(

    String type,
    LocalDate month,
    String clanTag

) {

    public static ClanWarMemberRecordQuery create(String type, Long month, String clanTag) {
        LocalDate searchMonth = TimeUtils.parseLocalDate(month);
        return new ClanWarMemberRecordQuery(type, searchMonth, clanTag);
    }

    public ClanWarMemberRecordSearchCriteria toSearchCriteria() {
        LocalDateTime from = TimeUtils.toFirstDayOfMonthDateTime(month);
        LocalDateTime to = TimeUtils.toLastDayOfMonthDateTime(month);
        return new ClanWarMemberRecordSearchCriteria(ClanWarType.NONE, clanTag, type, from, to);
    }
}
