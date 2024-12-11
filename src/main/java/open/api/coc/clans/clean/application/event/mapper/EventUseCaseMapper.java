package open.api.coc.clans.clean.application.event.mapper;

import open.api.coc.clans.clean.domain.event.model.EventTeamLegend;
import open.api.coc.clans.clean.presentation.event.dto.EventTeamLegendResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventUseCaseMapper {

    EventTeamLegendResponse toEventTeamLegendResponse(EventTeamLegend teamLegend);

}
