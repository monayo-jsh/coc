package open.api.coc.clans.clean.infrastructure.competition.persistence.mapper;

import open.api.coc.clans.clean.domain.competition.model.CompetitionClanRoaster;
import open.api.coc.clans.clean.infrastructure.competition.persistence.entity.CompetitionClanRoasterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CompetitionClanRoasterMapper {

    @Mapping(target = "id.compClanId", source = "compClanId")
    @Mapping(target = "id.playerTag", source = "playerTag")
    CompetitionClanRoasterEntity toEntity(CompetitionClanRoaster source);

    @Mapping(target = "compClanId", source = "id.compClanId")
    @Mapping(target = "playerTag", source = "id.playerTag")
    CompetitionClanRoaster toDomain(CompetitionClanRoasterEntity source);

}
