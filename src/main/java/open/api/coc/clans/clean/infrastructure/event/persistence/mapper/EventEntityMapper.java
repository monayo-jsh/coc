package open.api.coc.clans.clean.infrastructure.event.persistence.mapper;

import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        EventTeamLegendEntityMapper.class
    }
)
public interface EventEntityMapper {

    EventTeamLegend toEventTeamLegend(EventEntity eventEntity);

}
