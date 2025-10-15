package open.api.coc.clans.clean.presentation.player.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import open.api.coc.clans.clean.domain.league.model.League;

@Getter
@Builder
@AllArgsConstructor
public class LeagueDistributionResponse {

    private Integer leagueId; // 리그 ID
    private Long count; // 계정 수

}
