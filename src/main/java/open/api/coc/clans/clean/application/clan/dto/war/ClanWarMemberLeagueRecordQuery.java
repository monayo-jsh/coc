package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarMemberRecordSearchCriteria;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import open.api.coc.clans.domain.clans.converter.TimeUtils;

public record ClanWarMemberLeagueRecordQuery(

    String type,
    LocalDate month,
    String clanTag,
    Boolean isPerfect

) {

    public static ClanWarMemberLeagueRecordQuery create(String type, Long month, String clanTag, Boolean isPerfect) {
        LocalDate searchMonth = TimeUtils.parseLocalDate(month);
        return new ClanWarMemberLeagueRecordQuery(type, searchMonth, clanTag, isPerfect);
    }

    public ClanWarMemberRecordSearchCriteria toSearchCriteria() {
        LocalDateTime from = TimeUtils.toFirstDayOfMonthDateTime(month);
        LocalDateTime to = TimeUtils.toLastDayOfMonthDateTime(month);
        return new ClanWarMemberRecordSearchCriteria(ClanWarType.LEAGUE, clanTag, type, from, to);
    }

    public boolean isNotPerfect() {
        return !isPerfect;
    }
}
