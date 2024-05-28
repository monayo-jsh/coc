package open.api.coc.clans.domain.raid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.domain.players.PlayerClanResponse;

@Getter
@Builder
@AllArgsConstructor
public class RaidScoreResponse {

    private String name;
    private String tag;

    private PlayerClanResponse clan;

    private long seasonStartDate;
    private long seasonEndDate;

    private Integer attacks;
    private Integer resourceLooted;
}
