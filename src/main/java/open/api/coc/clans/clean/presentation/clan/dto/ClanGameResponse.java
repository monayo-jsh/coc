package open.api.coc.clans.clean.presentation.clan.dto;

import java.util.List;
import open.api.coc.clans.clean.domain.clan.model.ClanGameDTO;

public record ClanGameResponse(

    String progressDate,
    Integer completedPoint,
    List<ClanGameDTO> clanGames

) {
}
