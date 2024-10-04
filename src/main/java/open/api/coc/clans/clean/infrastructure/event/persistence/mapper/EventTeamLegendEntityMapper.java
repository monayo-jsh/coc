package open.api.coc.clans.clean.infrastructure.event.persistence.mapper;

import open.api.coc.clans.clean.domain.event.model.EventTeam;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
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

    EventTeam toEventTeamLegend(EventTeamLegendEntity teamLegendEntity);

    @AfterMapping
    default void afterMapping(EventTeamLegendEntity teamLegendEntity, @MappingTarget EventTeam.EventTeamBuilder eventTeamBuilder) {
        eventTeamBuilder.syncTotalTrophies();
    }
}
