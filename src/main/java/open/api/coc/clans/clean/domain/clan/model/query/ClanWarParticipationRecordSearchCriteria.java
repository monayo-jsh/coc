package open.api.coc.clans.clean.domain.clan.model.query;

import java.time.LocalDateTime;
import open.api.coc.clans.database.entity.clan.ClanWarType;
import org.springframework.data.domain.Pageable;

public record ClanWarParticipationRecordSearchCriteria(

    LocalDateTime preparationStartTime,
    LocalDateTime preparationEndTime,
    String playerTag,
    String playerName

) {

    public boolean hasPlayerTag() {
        return playerTag != null && !playerTag.isBlank();
    }
    public boolean hasPlayerName() { return playerName != null && !playerName.isBlank(); }

}
