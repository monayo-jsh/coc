package open.api.coc.clans.clean.domain.event.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamRankResponse;

public interface EventTeamLegendMemberRepository {

    void saveRecordHistory(List<EventTeamMember> members);

    List<EventTeamRankResponse> findAllTeamLegendDailyRankingsByIds(List<Long> teamIds);

}
