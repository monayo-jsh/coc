package open.api.coc.clans.clean.infrastructure.event.persistence.mapper;

import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventTeamLegendMemberHistoryEntityMapper {

    @Mapping(target = "teamLegendId", source = "teamId")
    @Mapping(target = "lastModifiedAt", source = "updatedAt")
    EventTeamLegendMemberHistoryEntity toEventTeamLegendMemberHistoryEntity(EventTeamMember teamMember);

}
