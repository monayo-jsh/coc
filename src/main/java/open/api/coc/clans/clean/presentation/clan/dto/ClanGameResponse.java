package open.api.coc.clans.clean.presentation.clan.dto;

import java.util.List;

public record ClanGameResponse(

    String progressDate,
    Integer completedPoint,
    List<ClanGameRecordResponse> records

) {
}
