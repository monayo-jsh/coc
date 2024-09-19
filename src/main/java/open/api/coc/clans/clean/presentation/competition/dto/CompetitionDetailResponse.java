package open.api.coc.clans.clean.presentation.competition.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter @Getter
public class CompetitionDetailResponse extends CompetitionResponse {

    private List<CompetitionClanResponse> participantClans;

}