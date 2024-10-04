package open.api.coc.clans.clean.infrastructure.event.persistence.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import open.api.coc.clans.clean.domain.event.model.EventTeam;
import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.domain.event.repository.EventTeamLegendRepository;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberEntity;
import open.api.coc.clans.clean.infrastructure.event.persistence.mapper.EventEntityMapper;
import open.api.coc.clans.clean.infrastructure.event.persistence.mapper.EventTeamLegendEntityMapper;
import open.api.coc.clans.clean.infrastructure.event.persistence.mapper.EventTeamLegendMemberEntityMapper;
import open.api.coc.clans.clean.infrastructure.event.persistence.repository.JpaEventCustomRepository;
import open.api.coc.clans.clean.infrastructure.event.persistence.repository.JpaEventRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventTeamLegendDatabaseService implements EventTeamLegendRepository {

    private final JpaEventRepository jpaEventRepository;
    private final JpaEventCustomRepository eventCustomRepository;

    private final EventEntityMapper eventEntityMapper;
    private final EventTeamLegendEntityMapper teamLegendEntityMapper;
    private final EventTeamLegendMemberEntityMapper teamLegendMemberEntityMapper;

    @Override
    public LocalDateTime findLatestStartDate() {
        return eventCustomRepository.findLatestStartDate();
    }

    @Override
    public EventTeamLegend findByStartDate(LocalDateTime startDate) {
        EventEntity teamLegendEntity = eventCustomRepository.findByStartDate(startDate);
        return eventEntityMapper.toEventTeamLegend(teamLegendEntity);
    }

    @Override
    public void save(EventTeamLegend teamLegend) {
        // 이벤트 엔티티 생성
        EventEntity eventEntity = eventEntityMapper.toEventEntity(teamLegend);

        // 이벤트 팀 엔티티 목록 생성 및 매핑
        List<EventTeamLegendEntity> teamEntities = teamLegend.getTeams().stream().map(this::makeEventTeamLegendEntity).toList();
        eventEntity.changeTeams(teamEntities);

        // 이벤트 저장
        jpaEventRepository.save(eventEntity);
    }

    private EventTeamLegendEntity makeEventTeamLegendEntity(EventTeam team) {
        // 팀 엔티티 생성
        EventTeamLegendEntity teamLegendEntity = teamLegendEntityMapper.toEventTeamLegendEntity(team);

        // 팀원 엔티티 생성 및 매핑
        List<EventTeamLegendMemberEntity> memberEntities = team.getMembers().stream().map(teamLegendMemberEntityMapper::toEventTeamLegendMemberEntity).toList();
        teamLegendEntity.changeMembers(memberEntities);

        return teamLegendEntity;
    }
}
