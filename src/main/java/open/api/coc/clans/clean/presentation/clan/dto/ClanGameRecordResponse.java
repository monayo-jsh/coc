package open.api.coc.clans.clean.presentation.clan.dto;

import java.time.LocalDateTime;

public record ClanGameRecordResponse(

    String tag,
    String name,
    Integer point,
    LocalDateTime achievementDate

) {

}
