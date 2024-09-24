package open.api.coc.clans.clean.domain.league.mapper;

import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.presentation.league.dto.LeagueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface LeagueMapper {

    @Mapping(target = "iconUrls", source = "iconUrl")
    LeagueResponse toResponse(League league);

}
