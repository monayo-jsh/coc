package open.api.coc.clans.clean.infrastructure.event.persistence.service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendMemberRepository;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberHistoryEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.mapper.EventTeamLegendMemberHistoryEntityMapper;
import open.api.coc.clans.clean.infrastructure.event.persistence.repository.JpaEventTeamLegendMemberHistoryRepository;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamRankResponse;
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

    @Override
    public List<EventTeamRankResponse> findAllTeamLegendDailyRankingsByIds(List<Long> teamIds) {
        List<Object[]> results = memberHistoryRepository.findAllTeamLegendDailyRankingsByIds(teamIds);
        return results.stream()
                      .map(result -> new EventTeamRankResponse(
                          (Date.valueOf((String) result[0])).toLocalDate(), // rankingDate
                          (Long) result[1], // teamId
                          (String) result[2], // teamName
                          ((Long) result[3]).intValue(), // trophies
                          ((Long) result[4]).intValue() // dailyRank
                      ))
                      .collect(Collectors.toList());
    }
}
