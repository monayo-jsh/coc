package open.api.coc.clans.database.entity.clan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import open.api.coc.clans.clean.infrastructure.league.persistence.entity.LeagueEntity;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClanAssignedPlayerDTO {

    private String seasonDate;
    private String playerTag;
    private String playerName;
    private Integer trophies;
    private LeagueEntity league;

    private ClanEntity clan;
    private ClanEntity joinedClan;

}
