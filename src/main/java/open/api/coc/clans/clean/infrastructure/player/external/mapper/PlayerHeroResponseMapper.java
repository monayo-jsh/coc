package open.api.coc.clans.clean.infrastructure.player.external.mapper;

import open.api.coc.clans.clean.infrastructure.player.external.model.PlayerHeroResponse;
import open.api.coc.clans.clean.domain.player.model.PlayerHero;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(
    config = MapStructConfig.class
)
public interface PlayerHeroResponseMapper {

    PlayerHero toPlayerHero(PlayerHeroResponse heroResponse);

}
