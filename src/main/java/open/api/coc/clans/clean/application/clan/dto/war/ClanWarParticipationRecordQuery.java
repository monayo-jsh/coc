package open.api.coc.clans.clean.application.clan.dto.war;

import java.time.LocalDate;
import java.time.LocalDateTime;
import open.api.coc.clans.clean.domain.clan.model.query.ClanWarParticipationRecordSearchCriteria;
import open.api.coc.clans.domain.clans.converter.TimeUtils;

public record ClanWarParticipationRecordQuery(

    LocalDate preparationStartDate,
    LocalDate preparationEndDate,
    String playerTag,
    String playerName

) {

    public static ClanWarParticipationRecordQuery create(Long startDate, Long endDate, String playerTag, String playerName) {
        LocalDate preparationStartDate = TimeUtils.parseLocalDate(startDate);
        LocalDate preparationEndDate = TimeUtils.parseLocalDate(endDate);

        return new ClanWarParticipationRecordQuery(preparationStartDate, preparationEndDate, playerTag, playerName);
    }

    public ClanWarParticipationRecordSearchCriteria toSearchCriteria() {
        LocalDateTime preparationStartTime = TimeUtils.withMinTime(preparationStartDate);
        LocalDateTime preparationEndTime = TimeUtils.withMaxTime(preparationEndDate);

        return new ClanWarParticipationRecordSearchCriteria(preparationStartTime, preparationEndTime, playerTag, playerName);
    }

}
