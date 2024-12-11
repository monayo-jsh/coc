package open.api.coc.clans.database.entity.clan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanAssignedPlayerDTO {

    private String seasonDate;
    private String playerTag;
    private String playerName;

    private ClanEntity clan;

}
