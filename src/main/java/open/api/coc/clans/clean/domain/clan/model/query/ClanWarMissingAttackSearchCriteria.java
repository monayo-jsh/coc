package open.api.coc.clans.clean.domain.clan.model.query;

import java.time.LocalDateTime;
import open.api.coc.clans.domain.clans.converter.TimeUtils;

public record ClanWarMissingAttackSearchCriteria(

    String tag,
    String name,
    LocalDateTime from,
    LocalDateTime to

) {

    public static ClanWarMissingAttackSearchCriteria create(String tag, String name, Integer queryDate) {
        LocalDateTime from = TimeUtils.getDateMinTimeDaysAgo(queryDate);
        LocalDateTime to = TimeUtils.getDateMaxTimeDaysAgo(0);
        return new ClanWarMissingAttackSearchCriteria(tag, name, from, to);
    }

    public boolean hasTag() {
        return tag != null && !tag.isBlank();
    }

    public boolean hasName() {
        return name != null && !name.isBlank();
    }

}
