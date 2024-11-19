package open.api.coc.clans.clean.domain.clan.model.query;

import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import org.springframework.data.domain.Pageable;

public record ClanWarMemberRecordSearchCriteria(

    ClanWarType clanWarType,
    String clanTag,
    String type,
    LocalDateTime from,
    LocalDateTime to
) {

    public boolean hasClanTag() {
        return clanTag != null && !clanTag.isBlank();
    }

    public Pageable makePageable(Integer limit) {
        if ("ALL".equalsIgnoreCase(type)) {
            return Pageable.unpaged();
        }

        return Pageable.ofSize(limit);
    }
}
