package open.api.coc.clans.clean.infrastructure.player.external.mapper;

import open.api.coc.clans.clean.infrastructure.player.external.model.PlayerHeroEquipmentResponse;
import open.api.coc.clans.clean.domain.player.model.PlayerHeroEquipment;
import open.api.coc.clans.common.config.MapStructConfig;
import org.mapstruct.Mapper;

@Mapper(
    config = MapStructConfig.class
)
public interface PlayerHeroEquipmentResponseMapper {

    PlayerHeroEquipment toPlayerHeroEquipment(PlayerHeroEquipmentResponse heroEquipmentResponse);


}
