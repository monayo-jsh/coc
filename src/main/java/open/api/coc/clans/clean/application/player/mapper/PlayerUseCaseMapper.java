package open.api.coc.clans.clean.application.player.mapper;

import open.api.coc.clans.clean.domain.clan.model.Clan;
import open.api.coc.clans.clean.domain.league.mapper.LeagueMapper;
import open.api.coc.clans.clean.domain.league.model.League;
import open.api.coc.clans.clean.domain.player.model.Player;
import open.api.coc.clans.clean.presentation.player.dto.PlayerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    uses = {
        LeagueMapper.class
    }
)
public interface PlayerUseCaseMapper {

    @Mapping(target = "tag", source = "player.tag")
    @Mapping(target = "name", source = "player.name")
    @Mapping(target = "clan", source = "clan")
    @Mapping(target = "league", source = "league")
    PlayerResponse toPlayerResponse(Player player, Clan clan, League league);

}
