package open.api.coc.clans.clean.infrastructure.event.persistence.mapper;

import open.api.coc.clans.clean.domain.event.model.EventTeamMember;
import open.api.coc.clans.clean.infrastructure.event.persistence.entity.EventTeamLegendMemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface EventTeamLegendMemberEntityMapper {

    @Mapping(target = "teamId", source = "team.id")
    EventTeamMember toEventTeamMember(EventTeamLegendMemberEntity teamLegendMemberEntity);

    EventTeamLegendMemberEntity toEventTeamLegendMemberEntity(EventTeamMember teamMember);

}
