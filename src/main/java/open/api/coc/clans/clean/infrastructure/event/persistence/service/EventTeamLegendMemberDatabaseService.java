package open.api.coc.clans.clean.infrastructure.event.persistence.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendMemberRepository;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberHistoryEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.mapper.EventTeamLegendMemberHistoryEntityMapper;
import open.api.coc.clans.clean.infrastructure.event.persistence.repository.JpaEventTeamLegendMemberHistoryRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventTeamLegendMemberDatabaseService implements EventTeamLegendMemberRepository {

    private final JpaEventTeamLegendMemberHistoryRepository memberHistoryRepository;

    private final EventTeamLegendMemberHistoryEntityMapper memberHistoryEntityMapper;

    @Override
    public void saveRecordHistory(List<EventTeamMember> members) {
        List<EventTeamLegendMemberHistoryEntity> memberHistoryEntities = members.stream()
                                                                                .map(memberHistoryEntityMapper::toEventTeamLegendMemberHistoryEntity)
                                                                                .toList();

        memberHistoryRepository.saveAll(memberHistoryEntities);
    }
}
