package open.api.coc.clans.clean.infrastructure.event.persistence.mapper;

import open.api.coc.clans.clean.domain.event.model.EventTeam;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        EventTeamLegendMemberEntityMapper.class
    }
)
public interface EventTeamLegendEntityMapper {

    @Mapping(target = "eventId", source = "event.id")
    EventTeam toEventTeamLegend(EventTeamLegendEntity teamLegendEntity);

    @Mapping(target = "members", ignore = true)
    EventTeamLegendEntity toEventTeamLegendEntity(EventTeam eventTeam);

    @AfterMapping
    default void afterMapping(EventTeamLegendEntity teamLegendEntity, @MappingTarget EventTeam.EventTeamBuilder eventTeamBuilder) {
        eventTeamBuilder.syncTotalTrophies();
    }
}
