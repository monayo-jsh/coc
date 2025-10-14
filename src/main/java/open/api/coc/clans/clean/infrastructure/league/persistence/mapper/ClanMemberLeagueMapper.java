package open.api.coc.clans.clean.infrastructure.league.persistence.mapper;

import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.infrastructure.common.external.dto.LeagueResponse;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    config = MapStructConfig.class
)
public interface ClanMemberLeagueMapper {

    @Mapping(target = "iconUrl", source = "iconUrls")
    League toLeague(LeagueResponse league);

}
