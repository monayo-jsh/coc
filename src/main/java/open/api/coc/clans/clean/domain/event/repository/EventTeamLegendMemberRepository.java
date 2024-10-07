package open.api.coc.clans.clean.domain.event.repository;

import java.util.List;
import open.api.coc.clans.clean.domain.event.model.EventTeamMember;

public interface EventTeamLegendMemberRepository {

    void saveRecordHistory(List<EventTeamMember> members);

//    List<EventTeamMemberHistory> findByMemberHistory(List<Long> teamIds);

}
